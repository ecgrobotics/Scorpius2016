package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.Timer;



//Handles all controls in autonomous mode
public class Autonomous {

	static Timer timer;
	static boolean timerSet, part1 = true, part2 = false, part3 = false, part4 = false;
	static double gyroAngle, maxRate = 3, maxOffset = 15, offset, startTime, runTime = 4000, accel = 1;
	private static ActuatorEncoder encoder;
	private static Accelerometer accelerometer;
	

	public static void Initialize () {
		gyroAngle = Gyro.GetAngle() * 360;
		timer = new Timer();
	}

	public static void Update () {
		//Perform AI Logic here
		if(part1){
			lowerArm();
			if(encoder.getAverageVoltage() < Actuator.bottomVoltage + .3 && encoder.getAverageVoltage() > Actuator.bottomVoltage - .3){
				part1 = false;
				part2 = true;
				startTime = System.currentTimeMillis();
			}
		}if(part2){
			correctObstacles();
			if(accelerometer.getX() > accel || System.currentTimeMillis() >= startTime + runTime){
			part2 = false;
			part3 = true;
			}
		}if(part3){
			shoot();
			part3 = false;
		}
		//Update values
	}


	//Utility methods
	static void swankDrive(double x, double y, double z){
		Swerve.Drive(x, y, z, false);
		Panzer.Drive(-x, -y);

	}
	static void lowerArm(){
		Actuator.downPosition();
	}
	static void correctObstacles(){
		if(gyroAngle < gyroAngle + maxOffset && gyroAngle > gyroAngle - maxOffset){
			swankDrive(0, .75, 0);
		}
		else swankDrive(0,0,0);
//		if(gyroAngle > gyroAngle + maxOffset){
//				swankDrive(0, -1, -.25);
//			}
//		else if(gyroAngle < gyroAngle - maxOffset){
//				swankDrive(0, -1, .25);
//		}
	}
	static void shoot(){
//		Actuator.shootPosition();
//		Stinger.runRollerMotor();
	}


	//Op methods


	static void SetAxis (double value, int axis, int gamepad) {
		//Fool Sensory into thinking that the axis on the gamepad is this value
		Sensory.axes[gamepad][axis] = value;
	}

	static void SetButton (boolean value, int button, int gamepad) {
		//Fool Sensory into thinking that the button on the gamepad is this value
		Sensory.buttons[gamepad][button] = value;
	}

	static void SetPOV (int value, int index, int gamepad) {
		//Fool Sensory into thinking that the POV on the gamepad is this value
		Sensory.povs[gamepad][index] = value;
	}
}