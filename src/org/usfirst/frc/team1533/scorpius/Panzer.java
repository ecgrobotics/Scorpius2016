package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive;

public class Panzer {
	
	//State vars
	static RobotDrive drive;
	static boolean fixedHeading; //MOVE //Implement in Sensory
	
	//Smoothing vars
	static double yTarg;
	static double xTarg;
		
	public static void Initialize () {
		drive = new RobotDrive(ConstantFactory.RobotMap.L_TREAD, ConstantFactory.RobotMap.R_TREAD);
	}
	
	public static void Update(){ //DEPLOY
		yTarg = Extensions.Lerp(yTarg, Sensory.GetAxis(AxisType.kX, Sensory.tankOverride() ? 0 : 1)*(Swerve.flipMotors ? -1 : 1), ConstantFactory.Panzer.HARDNESS_CONSTANT * 0.033);
		xTarg = Extensions.Lerp(xTarg, -Sensory.GetAxis(AxisType.kZ, Sensory.tankOverride() ? 0 : 1)*(Swerve.flipMotors ? -1 : 1), ConstantFactory.Panzer.HARDNESS_CONSTANT * 0.033); //not kTwist
		drive.arcadeDrive(yTarg, fixedHeading ? Gyro.GetAngle() * ConstantFactory.Panzer.HEADING_SCALE_FACTOR * -1 : xTarg, ConstantFactory.Panzer.APPLY_QUADRATIC_SCALING);
	}
	public static void lockWheels(){
		yTarg = 0;
		xTarg = 0;
	}
}