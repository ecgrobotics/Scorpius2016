package org.usfirst.frc.team1533.scorpius;

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
	public static void Drive(double xTarg, double yTarg){
		drive.arcadeDrive(yTarg, xTarg, true);
	}
	

	public static void Update(){ //DEPLOY
//		if(!SwerveBroken.encoderBroken){
//			yTarg = Extensions.Lerp(yTarg, -Sensory.GetAxis(1, Sensory.tankOverride() ? 0 : 1)*(Swerve.flipMotors ? -1 : 1), ConstantFactory.Panzer.HARDNESS_CONSTANT * 0.033);
//			xTarg = Extensions.Lerp(xTarg, -Sensory.GetAxis(2, Sensory.tankOverride() ? 0 : 1)*(Swerve.flipMotors ? -1 : 1), ConstantFactory.Panzer.HARDNESS_CONSTANT * 0.033); //not kTwist
//		}
//		else{
			yTarg = Extensions.Lerp(yTarg, -Sensory.GetAxis(1, 0)*(Swerve.flipMotors ? -1 : 1), ConstantFactory.Panzer.HARDNESS_CONSTANT * 0.033);
			xTarg = Extensions.Lerp(xTarg, -Sensory.GetAxis(2, 0)*(Swerve.flipMotors ? -1 : 1), ConstantFactory.Panzer.HARDNESS_CONSTANT * 0.033); //not kTwist
//		}
		Drive(yTarg, fixedHeading ? Gyro.GetAngle() * ConstantFactory.Panzer.HEADING_SCALE_FACTOR * -1 : xTarg);
	}
	public static void lockWheels(){
		yTarg = 0;
		xTarg = 0;
	}
}