package org.usfirst.frc.team1533.robot;

public class ConstantFactory {
	//Swerve
	public final static double FL_ENC_OFFSET = 353;
	public final static double FR_ENC_OFFSET = 305;
	public final static double BL_ENC_OFFSET = 217;
	public final static double BR_ENC_OFFSET = 132;
	
	public final static double WHEEL_BASE_WIDTH = 23;
	public final static double WHEEL_BASE_LENGTH = 16.5;
	
	//ButtonMap
	public final static int X = 1;
	public final static int A = 2;
	public final static int B = 3;
	public final static int Y = 4;
	
	public final static int LEFT_BUMPER = 5;
	public final static int RIGHT_BUMPER = 6;
	public final static int LEFT_TRIGGER = 7;
	public final static int RIGHT_TRIGGER = 8;
	
	//RobotMap
	public final static int FR_STEER = 0;
	public final static int FR_DRIVE = 1;
	public final static int BR_STEER = 2;
	public final static int BR_DRIVE = 3;
	public final static int FL_STEER = 4;
	public final static int FL_DRIVE = 5;
	public final static int BL_STEER = 6;
	public final static int BL_DRIVE = 7;
	
	
	public final static int FR_ENCODER = 0;
	public final static int BR_ENCODER = 1;
	public final static int FL_ENCODER = 2;
	public final static int BL_ENCODER = 3;
	public final static int ACTUATOR_ENCODER = 4;

	
	public final static int L_TREAD = 8;
	public final static int R_TREAD = 9;

	public final static int ACTUATOR = 10;

	public final static int STINGER_L = 12;
	public final static int STINGER_R = 11;
	public final static int ROLLER = 13;

	public final static int CLIMB_L = 1;
	public final static int CLIMB_R = 0;
	
	public static class Steering{
		//Swerve
		public final static double SWERVE_STEER_CAP = 1; 
		public final static double SWERVE_STEER_P = 2; 
		public final static double SWERVE_STEER_I = 0; 
		public final static double SWERVE_STEER_D = 0;
		
		//Tank
		public final static double HARDNESS_CONSTANT = 9.0;
		
		//Actuator
		public static double angleVoltage = 3.65;
		public static double initVoltage = 3.1;
		public static double hangVoltage = 3.8;
		public static double bottomVoltage = 1.2;
		
		//Stinger
		public final static double SHOOTER_DELAY = 0.5;
		public final static double STINGER_POWER_SHOOT_PERCENT = 1.0;
		public final static double STINGER_POWER_GRASP_PERCENT = 0.4;
	}
}
