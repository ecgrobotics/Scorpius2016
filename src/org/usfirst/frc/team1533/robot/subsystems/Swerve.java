package org.usfirst.frc.team1533.robot.subsystems;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team1533.robot.ConstantFactory;

/**
 * @author Duncan
 *
 */
public class Swerve extends Subsystem {
	double pivotX, pivotY, lastpressed, startangle;
	boolean lockwheels;
	public static boolean rotating, angle;
	public SwerveModule[] modules;
	Joystick joy1, joy2;
	Gyro gyro;
	SpeedController flDrive, frDrive, blDrive, brDrive, flsteer, frsteer, blsteer, brsteer;

	/**
	 * Custom constructor for current robot.
	 */
	public Swerve(Joystick joy1, Joystick joy2, Gyro gyro) {
		this.joy1 = joy1;
		this.joy2 = joy2;
		this.gyro = gyro;
		lockwheels = false;	
		rotating = false;
		lastpressed = System.currentTimeMillis() + 1000;
		//initialize array of modules
		//array can be any size, as long as the position of each module is specified in its constructor
		modules = new SwerveModule[] {
				//front left
				new SwerveModule(new Talon(ConstantFactory.FL_DRIVE),
						new Talon(ConstantFactory.FL_STEER),
						new AbsoluteEncoder(ConstantFactory.FL_ENCODER, ConstantFactory.FL_ENC_OFFSET),
						-ConstantFactory.WHEEL_BASE_WIDTH/2,
						ConstantFactory.WHEEL_BASE_LENGTH/2
						),
				//front right
				new SwerveModule(new Talon(ConstantFactory.FR_DRIVE), 
						new Talon(ConstantFactory.FR_STEER),
						new AbsoluteEncoder(ConstantFactory.FR_ENCODER, ConstantFactory.FR_ENC_OFFSET),
						ConstantFactory.WHEEL_BASE_WIDTH/2,
						ConstantFactory.WHEEL_BASE_LENGTH/2
						),
				//back left
				new SwerveModule(new Talon(ConstantFactory.BL_DRIVE),
						new Talon(ConstantFactory.BL_STEER),
						new AbsoluteEncoder(ConstantFactory.BL_ENCODER, ConstantFactory.BL_ENC_OFFSET),
						-ConstantFactory.WHEEL_BASE_WIDTH/2,
						-ConstantFactory.WHEEL_BASE_LENGTH/2
						),
				//back right
				new SwerveModule(new Talon(ConstantFactory.BR_DRIVE), 
						new Talon(ConstantFactory.BR_STEER),
						new AbsoluteEncoder(ConstantFactory.BR_ENCODER, ConstantFactory.BR_ENC_OFFSET),
						ConstantFactory.WHEEL_BASE_WIDTH/2,
						-ConstantFactory.WHEEL_BASE_LENGTH/2
						)
		};
		enable();
	}

	/**
	 * @param pivotX x coordinate in inches of pivot point relative to center of robot
	 * @param pivotY y coordinate in inches of pivot point relative to center of robot
	 */
	public void setPivot(double pivotX, double pivotY) {
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}

	public void debugMode(){

	}
	/**
	 * Drive with field oriented capability
	 * @param translationX relative speed in left/right direction (-1 to 1)
	 * @param translationY relative speed in forward/reverse direction (-1 to 1)
	 * @param rotation relative rate of rotation around pivot point (-1 to 1) positive is clockwise
	 * @param heading offset in heading in radians (used for field oriented control)
	 */
	private void driveWithOrient(double translationX, double translationY, double rotation, double heading) {
		Vector[] vects = new Vector[modules.length];
		Vector transVect = new Vector(translationX, translationY),
				pivotVect = new Vector(pivotX, pivotY);

		//if there is only one module ignore rotation
		if (modules.length < 2)
			for (SwerveModule module : modules) 
				module.set(transVect.getAngle(), Math.min(1, transVect.getMagnitude())); //cap magnitude at 1

		double maxDist = 0;
		for (int i = 0; i < modules.length; i++) {
			vects[i] = new Vector(modules[i].positionX, modules[i].positionY);
			vects[i].subtract(pivotVect); //calculate module's position relative to pivot point
			maxDist = Math.max(maxDist, vects[i].getMagnitude()); //find farthest distance from pivot
		}

		double maxPower = 1;
		for (int i = 0; i < modules.length; i++) {
			//rotation motion created by driving each module perpendicular to
			//the vector from the pivot point
			vects[i].makePerpendicular();
			//scale by relative rate and normalize to the farthest module
			//i.e. the farthest module drives with power equal to 'rotation' variable
			vects[i].scale(rotation / maxDist);
			vects[i].add(transVect);
			//calculate largest power assigned to modules
			//if any exceed 100%, all must be scale down
			maxPower = Math.max(maxPower, vects[i].getMagnitude());
		}


		double power;
		for (int i = 0; i < modules.length; i++) {
			power = vects[i].getMagnitude() / maxPower; //scale down by the largest power that exceeds 100%
			if (power > .05) {
				modules[i].set(vects[i].getAngle()-Math.PI/2, power);
			} else {
				modules[i].rest();
			}
		}
	}

	/**
	 * Regular robot oriented control.
	 * @param translationX relative speed in left/right direction (-1 to 1)
	 * @param translationY relative speed in forward/reverse direction (-1 to 1)
	 * @param rotation relative rate of rotation around pivot point (-1 to 1) positive is clockwise
	 */
	public void driveNormal(double translationX, double translationY, double rotation) {
		driveWithOrient(translationX, translationY, rotation, 0);
	}

	public void enable() {
		for (SwerveModule module : modules) module.enable();
	}

	public void disable() {
		for (SwerveModule module : modules) module.disable();
	}
	public void autonomous(double x, double y, double z){
		driveNormal(x, y, z);
	}
	public void pivot(double degrees){
		double currentangle = gyro.getAngle();
		double targetangle = startangle + degrees;
		driveNormal(0,0,(targetangle-currentangle)/180);
		if((targetangle-currentangle)/180 < .04) rotating = false;
	}

	public void move(Tank tank){
		//		if(!SmartDashboard.getBoolean("DEBUG MODE")){
		int i = -1;
		if(joy1.getPOV() == 0){
			if(i<0){
				angle = true;
				i = 1;
			}
			driveNormal(0, .6, gyro.straight(angle));

			rotating = false;
			lockwheels = false;
		}
		else if(joy1.getPOV() == 180){
			if(i<0){
				angle = true;
				i = 1;
			}
			driveNormal(0, -.6, gyro.straight(angle));

			rotating = false;
			lockwheels = false;
		}
		else if(joy1.getRawButton(ConstantFactory.RIGHT_TRIGGER)||joy2.getRawButton(ConstantFactory.LEFT_TRIGGER2)){
			lockWheels();
			rotating = false;
		}else if(joy1.getRawButton(ConstantFactory.RIGHT_BUMPER)){
			fullPower();

			rotating = false;
			lockwheels = false;
		}else if(joy1.getRawButton(ConstantFactory.LEFT_BUMPER)){
			startangle = gyro.getAngle();
			lockwheels = false;
			rotating = true;
		}
		else {
			if(Math.abs(joy1.getX())>.05 || Math.abs(joy1.getY())>.05 || Math.abs(joy1.getRawAxis(3))>.05){
				driveNormal((joy1.getX()*60)/100, (-joy1.getY()*60)/100, (joy1.getRawAxis(3)/2));
				lockwheels = false;
				rotating = false;
			}
			else if(lockwheels) lockWheels();
			else if(rotating) pivot(180);
			else driveNormal(0,0,0);
		}
	}
	public void stop(int module){
		modules[module].driveController.set(0);
		modules[module].steerController.set(0);
	}
	public void fullPower(){
		driveNormal(joy1.getX()*9/10, -joy1.getY()*9/10, joy1.getRawAxis(3)*55/100);
	}

	public void lockWheels(){
		modules[0].set(45, 0);
		modules[1].set(-45, 0);
		modules[2].set(-45, 0);
		modules[3].set(45, 0);
	}
	public void fieldOrientation(){
		
	}

	/**
	 * 2D Mathematical Vector
	 */
	private class Vector {
		double x = 0, y = 0;

		public Vector(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getAngle() {
			return Math.atan2(y, x);
		}

		public double getMagnitude() {
			return Math.hypot(x, y);
		}

		public void scale(double scalar) {
			x *= scalar;
			y *= scalar;
		}

		public void add(Vector v) {
			x += v.x;
			y += v.y;
		}

		public void subtract(Vector v) {
			x -= v.x;
			y -= v.y;
		}

		public void makePerpendicular() {
			double temp = x;
			x = y;
			y = -temp;
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}

