package org.usfirst.frc.team1533.scorpius;

//This class keeps all constants
public class ConstantFactory {

	public static class Swerve {
		public final static double SWERVE_STEER_P = 2; //UPDATE
		public final static double SWERVE_STEER_I = 0; //UPDATE
		public final static double SWERVE_STEER_D = 0; //UPDATE
		public final static double SWERVE_STEER_CAP = 1; //speed limit on the steering motor

		//swerve module encoder offsets (degrees)
		public final static double FL_ENC_OFFSET = 59+14+180+26-3+80-3;
		public final static double FR_ENC_OFFSET = 103-13+70-10+180-25;
		public final static double BL_ENC_OFFSET = 98-85+90+6-15-5-5-2+135;
		public final static double BR_ENC_OFFSET = 135+13+91+3+80+180-10;

		//wheel base dimensions measures where the wheels touch the ground
		public final static double WHEEL_BASE_WIDTH = 23;
		public final static double WHEEL_BASE_LENGTH = 16.5;

		public final static double SLOW_TURN_PERCENT_MAX = 0.25;
	}

	public static class Panzer {
		public final static double HEADING_SCALE_FACTOR = 0.03; //DEPLOY
		public final static boolean APPLY_QUADRATIC_SCALING = true;
		public final static double HARDNESS_CONSTANT = 9.0;
	}

	public static class Actuator {
		public final static double P = 1.0;
		public final static double I = 0.0;
		public final static double D = 0.0;
		public final static double ANGLE_MINIMUM = 0.0; //UPDATE
		public final static double ANGLE_MAXIMUM = 90.0; //UPDATE
		public final static double ANGLE_EQUALITY_THRESHOLD = 1.0; //UPDATE
		public final static double POWER_MAGNITUDE_MAXIMUM = 1.0; //UPDATE
		public final static double HARDNESS_CONSTANT = 4.8;
		public final static double ACTUATOR_STINGER_BASE_LENGTH = 3.0;
		public final static double STINGER_BASE_PIVOT_LENGTH = 4.0;
	}

	public static class Stinger {
		public final static double SHOOTER_DELAY = 0.5;
		public final static double STINGER_POWER_SHOOT_PERCENT = 1.0;
		public final static double STINGER_POWER_GRASP_PERCENT = 0.4;
	}
	public static class Sensory {
		public final static double AXIS_SENSITIVITY_THRESHOLD = 0.05;
		public final static int AXIS_SEARCH_MAPPING_ID_MAX = 4;
		public final static int BUTTON_SEARCH_MAPPING_ID_MAX = 12;
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
		public final static int ACTUATOR_ENCODER = 4; //DEPLOY //Hardware

		public final static int L_TREAD = 8;
		public final static int R_TREAD = 9;

		public final static int ACTUATOR = 10;

		public final static int STINGER_L = 12;
		public final static int STINGER_R = 11;
		public final static int ROLLER = 13;

		public final static int CLIMB_L = 1;
		public final static int CLIMB_R = 0; 
	}
}
