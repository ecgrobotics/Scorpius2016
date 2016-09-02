package org.usfirst.frc.team1533.robot;

public class ConstantFactory {
	//Swerve
	public final static double FL_ENC_OFFSET = 353+84 - 6 -180;
	public final static double FR_ENC_OFFSET = 305 +52+180-46+1+2-14+132+20+132+90-7;
	public final static double BL_ENC_OFFSET = 217+49-25+50+2-40+180;
	public final static double BR_ENC_OFFSET = 132+220-7-180+155-51-29-244+10-3+6;
	
	public final static double WHEEL_BASE_WIDTH = 23;
	public final static double WHEEL_BASE_LENGTH = 16.5;
	
	//ButtonMap
	//square
	public final static int X = 4;
	//ecks
	public final static int A = 3;
	//circle
	public final static int B = 2;
	//triangle
	public final static int Y = 1;
	
	
	public final static int X2 = 1;
	//ecks
	
	public final static int A2 = 2;
	//circle
	public final static int B2 = 3;
	//triangle
	public final static int Y2 = 4;
	
	
	public final static int LEFT_BUMPER2 = 5;
	public final static int RIGHT_BUMPER2 = 6;
	public final static int LEFT_TRIGGER2 = 7;
	public final static int RIGHT_TRIGGER2 = 8;
	
	
	public final static int LEFT_BUMPER = 7;
	public final static int RIGHT_BUMPER = 8;
	public final static int LEFT_TRIGGER = 5;
	public final static int RIGHT_TRIGGER = 6;
	
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
	
	public final static int FLASHLIGHT = 2;

	
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
		public static double hangVoltage = .5;
		public static double bottomVoltage = 1.3;
		
		//Stinger
		public final static double SHOOTER_DELAY = 0.5;
		public final static double STINGER_POWER_SHOOT_PERCENT = 1.0;
		public final static double STINGER_POWER_GRASP_PERCENT = 0.4;
	}
}