package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.Timer;



//Handles all controls in autonomous mode
public class Autonomous {

	static Timer timer;
	static boolean timerSet, part1 = true, part2 = false, part3 = false, part4 = false, gyro = true;
	static double gyroAngle, maxRate = 3, maxOffset = 10, offset, startTime1, startTime2, runTime = 15000, accel = 1;
	private static Accelerometer accelerometer;
	static int number;
	static double rotation;
	

	public static void Initialize () {
		Gyro.gyro.reset();
		part1 = true;
		part2 = true;
		part3 = false;
		part4 = false;
		gyro = true;
		gyroAngle = Gyro.GetAngle();
		startTime1 = System.currentTimeMillis();
		}

	public static void Update () {
		//Perform AI Logic here
		if(part1){
			lowerArm();
			if(Actuator.encoder.getAverageVoltage() < Actuator.bottomVoltage + .3 && Actuator.encoder.getAverageVoltage() > Actuator.bottomVoltage - .3){
				part1 = false;
				Actuator.actuator.set(0);
			}
		}if(part2){
			correctObstacles();
			if(System.currentTimeMillis() >= startTime1 + runTime){
			part2 = false;
			part1 = false;
			part3 = true;
			}
		}if(part3){
			swankDrive(0,0,0);
			part3 = false;
		}
		//Update values
	}


	//Utility methods
	static void swankDrive(double x, double y, double z){
		rotation = Gyro.GetAngle() * -.025;
		
//		Swerve.Drive(x, y, (y==0) ? 0 : rotation, false);
		Panzer.Drive((y == 0)? 0 : -1, x);

	}
	static void lowerArm(){
		double target = Math.max(-1, Math.min((Actuator.bottomVoltage-Actuator.encoder.getAverageVoltage())*1, 1));	
		Actuator.actuator.set(target);
	}

	static void correctObstacles(){
		if((gyroAngle < (Gyro.GetAngle() + maxOffset) && gyroAngle > (Gyro.GetAngle() - maxOffset)) && (System.currentTimeMillis() < startTime1 + runTime)){
			swankDrive(0, -.5, 0);
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