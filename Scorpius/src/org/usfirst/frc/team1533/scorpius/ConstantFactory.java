package org.usfirst.frc.team1533.scorpius;

import com.ni.vision.NIVision;

//This class keeps all constants
public class ConstantFactory {
	
	public static class Camera {
		public final static String NAME = "cam0";
		public final static int FRAME_RATE = 30;
		public final static int RESOLUTION_WIDTH = 640;
		public final static int RESOLUTION_HEIGHT = 480;
	}
	
	public static class Lucid {
		public final static double CAMERA_FOV_X = 49.0d;
		public final static double CAMERA_FOV_Y = 45.0d; //UPDATE
		public final static double CAMERA_VIEWPORT_X_MIN = 0;
		public final static double CAMERA_VIEWPORT_X_MAX = 1;
		public final static double GRAVITY = 9.8d;
		public final static double CANNON_VELOCITY = 120d;
		public final static double TARGET_WIDTH = 20d; //In feet
	}
	
	public static class Photoduct { //UPDATE //Threshold cutoffs
		public final static double AREA_MINIMUM = 0.1; //UPDATE
		public final static NIVision.Range HUE_RANGE = new NIVision.Range(89, 149); //119 +/- 30
		public final static NIVision.Range SAT_RANGE = new NIVision.Range(223, 255); //244 - 20 +/- 20 //UPDATE //Increase range
		public final static NIVision.Range VAL_RANGE = new NIVision.Range(178, 238); //208 +/- 30 //UPDATE //Increase range
	}
	
	public static class Swerve {
		public final static double SWERVE_STEER_P = 1; //UPDATE
		public final static double SWERVE_STEER_I = 0; //UPDATE
		public final static double SWERVE_STEER_D = 0; //UPDATE
		public final static double SWERVE_STEER_CAP = 1; //speed limit on the steering motor
		
		//swerve module encoder offsets (degrees)
		public final static double FL_ENC_OFFSET = 79;
		public final static double FR_ENC_OFFSET = 208;
		public final static double BL_ENC_OFFSET = 138;
		public final static double BR_ENC_OFFSET = 42;
		
		//wheel base dimensions measures where the wheels touch the ground
		public final static double WHEEL_BASE_WIDTH = 23;
		public final static double WHEEL_BASE_LENGTH = 16.5;
	}
	
	public static class Panzer {
		public final static double HEADING_SCALE_FACTOR = 0.03; //UPDATE
		public final static boolean APPLY_QUADRATIC_SCALING = true; //DEPLOY
	}
	
	public static class Actuator {
		public final static double P = 1.0;
		public final static double I = 0.0;
		public final static double D = 0.0;
		public final static double ANGLE_MINIMUM = 0.0; //UPDATE
		public final static double ANGLE_MAXIMUM = 90.0; //UPDATE
		public final static double ANGLE_EQUALITY_THRESHOLD = 1.0; //UPDATE
		public final static double POWER_MAGNITUDE_MAXIMUM = 1.0; //UPDATE
	}
	
	public static class Stinger {
		public final static double SHOOTER_TIMEOUT = 1.2; //UPDATE //seconds
		public final static double STINGER_POWER_MAXIMUM_PERCENT = 1.0; //UPDATE
	}
	public static class Sensory {
		public final static int BUTTON_SEARCH_MAPPING_ID_MAX = 15;
	}
	
	public static class RobotMap {
		public final static int FL_STEER = 4;
		public final static int FR_STEER = 0;
		public final static int BL_STEER = 6;
		public final static int BR_STEER = 2;
		
		public final static int FL_DRIVE = 5;
		public final static int FR_DRIVE = 1;
		public final static int BL_DRIVE = 7;
		public final static int BR_DRIVE = 3;

		public final static int FL_ENCODER = 2;
		public final static int FR_ENCODER = 0;
		public final static int BL_ENCODER = 3;
		public final static int BR_ENCODER = 1;
		
		public final static int CLIMB_L = 1;
		public final static int CLIMB_R = 0;
		
		public final static int L_TREAD = 8;
		public final static int R_TREAD = 9;
		
		public final static int ACTUATOR = 10;
		public final static int ACTUATOR_ENCODER = 11;
		
		public final static int STINGER_L = 11;
		public final static int STINGER_R = 12;
		public static final int ROLLER = 13;
	}
}
