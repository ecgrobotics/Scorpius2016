package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Swerve {
	
	//State vars
	static double pivotX, pivotY;
	static SwerveModule[] modules;
	static boolean fieldOrientation = false;
	
	public static void Initialize () {
		//initialize array of modules
		//Array can be any size, as long as the position of each module is specified in its constructor
		modules = new SwerveModule[] {
			//front left
			new SwerveModule(new Talon(ConstantFactory.RobotMap.FL_DRIVE),
					new Talon(ConstantFactory.RobotMap.FL_STEER),
					new AbsoluteEncoder(ConstantFactory.RobotMap.FL_ENCODER, ConstantFactory.Swerve.FL_ENC_OFFSET),
					-ConstantFactory.Swerve.WHEEL_BASE_WIDTH/2,
					ConstantFactory.Swerve.WHEEL_BASE_LENGTH/2
					),
			//front right
			new SwerveModule(new Talon(ConstantFactory.RobotMap.FR_DRIVE), 
					new Talon(ConstantFactory.RobotMap.FR_STEER),
					new AbsoluteEncoder(ConstantFactory.RobotMap.FR_ENCODER, ConstantFactory.Swerve.FR_ENC_OFFSET),
					ConstantFactory.Swerve.WHEEL_BASE_WIDTH/2,
					ConstantFactory.Swerve.WHEEL_BASE_LENGTH/2
					),
			//back left
			new SwerveModule(new Talon(ConstantFactory.RobotMap.BL_DRIVE),
					new Talon(ConstantFactory.RobotMap.BL_STEER),
					new AbsoluteEncoder(ConstantFactory.RobotMap.BL_ENCODER, ConstantFactory.Swerve.BL_ENC_OFFSET),
					-ConstantFactory.Swerve.WHEEL_BASE_WIDTH/2,
					-ConstantFactory.Swerve.WHEEL_BASE_LENGTH/2
					),
			//back right
			new SwerveModule(new Talon(ConstantFactory.RobotMap.BR_DRIVE), 
					new Talon(ConstantFactory.RobotMap.BR_STEER),
					new AbsoluteEncoder(ConstantFactory.RobotMap.BR_ENCODER, ConstantFactory.Swerve.BR_ENC_OFFSET),
					ConstantFactory.Swerve.WHEEL_BASE_WIDTH/2,
					-ConstantFactory.Swerve.WHEEL_BASE_LENGTH/2
					)
		};
		enable();
	}
	
	/**
	 * @param pivoX x coordinate in inches of pivot point relative to center of robot
	 * @param pivtY y coordinate in inches of pivot point relative to center of robot
	 */
	public static void setPivot(double pivtX, double pivtY) {
		pivotX = pivtX;
		pivotY = pivtY;
	}
	
	/**
	 * Drive with field oriented capability
	 * @param translationX relative speed in left/right direction (-1 to 1)
	 * @param translationY relative speed in forward/reverse direction (-1 to 1)
	 * @param rotation relative rate of rotation around pivot point (-1 to 1) positive is clockwise
	 * @param heading offset in heading in radians (used for field oriented control)
	 */
	//INCOMPLETE //UPDATE
	private static void Drive (double translationX, double translationY, double rotation, boolean fieldOriented) {
		//Calculate a heading-compensation vector
		Vector2 correctOrientation = CorrectOrientationVector(translationX, translationY);
		//Check that we can apply heading compensation and apply
		translationX = fieldOriented ? correctOrientation.x: translationX;
		translationY = fieldOriented ? correctOrientation.y : translationY;
		//Initialize vector arrays //UPDATE //Bad for GC, don't allocate in each frame
		Vector2[] vects = new Vector2[modules.length];
		Vector2 transVect = new Vector2(translationX, translationY),
				pivotVect = new Vector2(pivotX, pivotY);
		
		//if there is only one module ignore rotation
		if (modules.length < 2)
			for (SwerveModule module : modules) 
				module.set(transVect.GetAngle(), Math.min(1, transVect.GetMagnitude())); //cap magnitude at 1

		double maxDist = 0;
		for (int i = 0; i < modules.length; i++) {
			vects[i] = new Vector2(modules[i].positionX, modules[i].positionY);
			vects[i].Subtract(pivotVect); //calculate module's position relative to pivot point
			maxDist = Math.max(maxDist, vects[i].GetMagnitude()); //find farthest distance from pivot
		}
		
		double maxPower = 1;
		for (int i = 0; i < modules.length; i++) {
			//rotation motion created by driving each module perpendicular to
			//the vector from the pivot point
			vects[i].MakePerpendicular();
			//scale by relative rate and normalize to the farthest module
			//i.e. the farthest module drives with power equal to 'rotation' variable
			vects[i].Scale(rotation / maxDist);
			vects[i].Add(transVect);
			//calculate largest power assigned to modules
			//if any exceed 100%, all must be scale down
			maxPower = Math.max(maxPower, vects[i].GetMagnitude());
		}
		
		double power;
		for (int i = 0; i < modules.length; i++) {
			power = vects[i].GetMagnitude() / maxPower; //scale down by the largest power that exceeds 100%
			if (power > .01) {
				modules[i].set(vects[i].GetAngle(), power);
			} else {
				modules[i].set(modules[i].steerEncoder.getAngle(), 0); 
			}
			SmartDashboard.putNumber("Encoder "+i, modules[i].steerEncoder.getVoltage());
		}
	}
	
	//Graciously plaigarized from Duncan's code :D
	private static Vector2 CorrectOrientationVector (double x, double y) {
		double angle = (-Gyro.GetAngle()) * Math.PI / 180;
		return new Vector2 (x*Math.cos(angle) - y*Math.sin(angle), x*Math.sin(angle) + y*Math.cos(angle));
	}
	
	public static void enable() {
		for (SwerveModule module : modules) module.enable();
	}
	
	public static void disable() {
		for (SwerveModule module : modules) module.disable();
	}
	
	public static void Update () {
		//DEBUG
		
		//Scheduler for PID
		Scheduler.getInstance().run();
		//Check that gamepad 1 isn't driving
		boolean overrideControl = Sensory.Gamepad0Driving();
		Drive(overrideControl ? Sensory.swerve.x : Sensory.panzer.left, Sensory.swerve.y, Sensory.swerve.z, fieldOrientation);
	}
	
	//This receives all Gamepad Button Down events from Sensory
	//This could be called several times per frame
	public static void OnButtonDown (ButtonMapping button, int gamepad) {
		//Gamepad 0 controls Swerve
		if (gamepad == 0) {
			//Reset forward orientation
			if (button.equals(ButtonMapping.L1)) {
				Gyro.Reset();
			}
			//Toggle field orientation
			if (button.equals(ButtonMapping.R2)) {
				fieldOrientation = !fieldOrientation;
			}
		}
	}

}
