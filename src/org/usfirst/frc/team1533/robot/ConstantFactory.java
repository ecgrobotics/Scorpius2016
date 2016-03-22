package org.usfirst.frc.team1533.robot;

public class ConstantFactory {
	//Swerve
	public final static double FL_ENC_OFFSET = 353;
	public final static double FR_ENC_OFFSET = 305;
	public final static double BL_ENC_OFFSET = 217;
	public final static double BR_ENC_OFFSET = 132;
	
	public final static double WHEEL_BASE_WIDTH = 23;
	public final static double WHEEL_BASE_LENGTH = 16.5;
	
	//RobotMap
	
	public final static int FR_STEER = 0;
	public final static int FR_DRIVE = 1;
	public final static int BR_STEER = 2;
	public final static int BR_DRIVE = 3;
	public final static int FL_STEER = 4;
	public final static int FL_DRIVE = 5;
	public final static int BL_STEER = 6;
	public final static int BL_DRIVE = 7;
	
	//Encoders
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
		public final static double SWERVE_STEER_P = 2; 
		public final static double SWERVE_STEER_I = 0; 
		public final static double SWERVE_STEER_D = 0; 
		public final static double SWERVE_STEER_CAP = 1; 
		
		//Tank
		public final static double HARDNESS_CONSTANT = 9.0;
		
		//Actuator
		
	}
}
