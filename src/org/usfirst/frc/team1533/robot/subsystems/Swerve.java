package org.usfirst.frc.team1533.robot.subsystems;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1533.robot.ConstantFactory;
import org.usfirst.frc.team1533.robot.Robot;

/**
 * @author Duncan
 *
 */
public class Swerve extends Subsystem {
	public double pivotX, pivotY, lastpressed, transAngle, pivotspeed;
	public static double angleRotation, startangle;
	boolean lockwheels, drivingField, ypressed;
	public static boolean rotating, angle;
	public SwerveModule[] modules;
	boolean fieldOrientation = false;
	Vector tankVector;
	Joystick joy1, joy2;
	Gyro gyro;
	SpeedController flDrive, frDrive, blDrive, brDrive, flsteer, frsteer, blsteer, brsteer;
	PIDController pid;
	Vision vision;

	/**
	 * Custom constructor for current robot.
	 */
	public Swerve(Joystick joy1, Joystick joy2, Gyro gyro, Vision vision) {
		this.joy1 = joy1;
		this.joy2 = joy2;
		this.vision = vision;
		this.gyro = gyro;
		ypressed = false;
		lockwheels = false;	
		drivingField = false;
		angleRotation = 0;
		rotating = false;
		lastpressed = System.currentTimeMillis() + 1000;
//		pid = new PIDController(1, 0, 3, gyro, pivotspeed);
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
	private void driveWithOrient(double translationX, double translationY, double rotation, boolean fieldOrientation) {
		Vector correctOrientation = correctOrientationVector(translationX, translationY);
		translationX = fieldOrientation ? correctOrientation.x: translationX;
		translationY = fieldOrientation ? correctOrientation.y : translationY;
		Vector[] vects = new Vector[modules.length];
		Vector transVect = new Vector(translationX, translationY),
				pivotVect = new Vector(pivotX, pivotY);
		setTrans(transVect);


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
				setTrans(vects[i]);
				modules[i].set(vects[i].getAngle()-Math.PI/2, power);
			} else {
				modules[i].rest();
			}
		}
	}
	public void setTrans(Vector vector){
		this.tankVector = vector;
	}
	public void setTransAngle(Vector vector){
		this.transAngle = vector.getAngle();
	}
	public double getTransAngle(){
		return transAngle;
	}
	public Vector getTrans(){
		return tankVector;
	}

	/**
	 * Regular robot oriented control.
	 * @param translationX relative speed in left/right direction (-1 to 1)
	 * @param translationY relative speed in forward/reverse direction (-1 to 1)
	 * @param rotation relative rate of rotation around pivot point (-1 to 1) positive is clockwise
	 */
	private Vector correctOrientationVector(double x, double y) {
		double angle = gyro.getAngle() * Math.PI / 180;
		return new Vector (x*Math.cos(angle) - y*Math.sin(angle), x*Math.sin(angle) + y*Math.cos(angle));
	}

	public void driveNormal(double translationX, double translationY, double rotation) {
		driveWithOrient(translationX, translationY, rotation, false);
	}
	public void driveField(double translationX, double translationY, double rotation){
		driveWithOrient(translationX, translationY, rotation, true);
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
//		driveNormal(0,0,(targetangle-currentangle)/180);
		driveNormal(0, 0, Math.max(-.25, Math.min(.25, (targetangle-currentangle)/180 - gyro.getRate()/180)));
		if(Math.abs(targetangle-currentangle)/180 < .04){
			rotating = false;
			driveNormal(0,0,0);
		}
		SmartDashboard.putNumber("target angle", targetangle);
		SmartDashboard.putNumber("current angle", currentangle);
		SmartDashboard.putNumber("ratio", (targetangle-currentangle)/degrees);


	}

	public void move(){
		//		if(!SmartDashboard.getBoolean("DEBUG MODE")){

		SmartDashboard.putBoolean("fieldorient", drivingField);
		SmartDashboard.putNumber("Current Gyro Angle", gyro.getAngle());
		SmartDashboard.putNumber("Angle to rotate to", angleRotation);


		int i = -1;
		if(joy1.getPOV() == 0){
			if(i<0){
				angle = true;
				i = 1;
			}
			driveNormal(0, .3, gyro.straight(angle));
			rotating = false;
			lockwheels = false;
			return;
		}else if(joy1.getPOV() == 180){
			//set turn robot to gyro 0
			if(gyro.getAngle() != 0) startangle = (Math.round(gyro.getAngle()/360))*360;
			else startangle = 0;
			SmartDashboard.putNumber("Starting base", startangle);

			angleRotation = 180;
			lockwheels = false;
			rotating = true;
		}
		else if(joy1.getPOV() == 90){
			if(i<0){
				angle = true;
				i = 1;
			}
			double z = joy1.getRawAxis(3);
			if (Math.abs(z) < .2) z = 0;
			double diff = Robot.ballSenseRight.getAverageVoltage()-Robot.ballSenseLeft.getAverageVoltage();
			driveNormal(0, .3-.03*Math.abs(diff), .03*diff+z*.3);
			rotating = false;
			lockwheels = false;
			return;
			
			//set turn robot to gyro -20
//			if(gyro.getAngle() != 0) startangle = (Math.round(gyro.getAngle()/360))*360;
//			else startangle = 0;
//			angleRotation = 120;
//			lockwheels = false;
//			rotating = true;
		}
		else if(joy1.getPOV() == 270){
			//set turn robot to gyro 20
			if(gyro.getAngle() != 0) startangle = (Math.round(gyro.getAngle()/360))*360;
			else startangle = 0;
			angleRotation = -120;
			lockwheels = false;
			rotating = true;
		}
		else if(joy1.getRawButton(ConstantFactory.B)){
			//set turn robot to gyro 20
			if(gyro.getAngle() != 0) startangle = (Math.round(gyro.getAngle()/360))*360;
			else startangle = 0;
			angleRotation = gyro.getAngle()+vision.horizontal()-startangle;
			lockwheels = false;
			rotating = true;
		}

		
		//if released toggle field orient

		if(joy1.getRawButton(ConstantFactory.X)){	
			System.out.println("y pressed");
			if (!ypressed) drivingField = !drivingField;
			ypressed = true;
		}else{
			ypressed = false;
		}
		if(joy1.getRawButton(ConstantFactory.X)) gyro.reset();
		if(joy1.getRawButton(ConstantFactory.RIGHT_TRIGGER)||joy2.getRawButton(ConstantFactory.LEFT_TRIGGER2)){
			lockWheels();
			rotating = false;
		}else if(joy1.getRawButton(ConstantFactory.RIGHT_BUMPER)){
			fullPower();
			rotating = false;
			lockwheels = false;
		}else if(joy1.getRawButton(ConstantFactory.LEFT_BUMPER)){
			//pivot 180
			startangle = gyro.getAngle();
			angleRotation = 180;
			lockwheels = false;
			rotating = true;
		}
		else {
			// p.s. the dead zone got bigger
			double x = joy1.getX();
			double y = joy1.getY();
			double z = joy1.getRawAxis(3);

			if((Math.abs(x) > .5 || Math.abs(y)>.5 || Math.abs(z)>.5) && !drivingField){
				driveNormal((x*60)/100, (-y*60)/100, (z/2));
				lockwheels = false;
				rotating = false;
			}
			else if((Math.abs(x) > .5 || Math.abs(y)>.5 || Math.abs(z)>.5) && drivingField){
				driveField((x*60)/100, (-y*60)/100, (z/2));
				lockwheels = false;
				rotating = false;
			}			else if(lockwheels) lockWheels();
			else if(rotating) pivot(angleRotation);
			else driveNormal(0,0,0);
		}
	}
	public void stop(int module){
		modules[module].driveController.set(0);
		modules[module].steerController.set(0);
	}
	public void fullPower(){
		if(!drivingField)
		driveNormal(joy1.getX()*9/10, -joy1.getY()*9/10, joy1.getRawAxis(3)*55/100);
		else
			driveWithOrient(joy1.getX()*9/10, -joy1.getY()*9/10, joy1.getRawAxis(3)*55/100, true);
	}

	public void lockWheels(){
		modules[1].set(-45, 0);
		modules[2].set(-45, 0);
		modules[3].set(45, 0);
	}

	/**
	 * 2D Mathematical Vector
	 */
	class Vector {
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