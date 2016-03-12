package org.usfirst.frc.team1533.scorpius;

import javax.rmi.CORBA.Util;

import edu.wpi.first.wpilibj.Timer;

//Handles all controls in autonomous mode
public class Egghead {
	
	static double startTime;
	static double endTime = 1000;
	
	public static void Initialize () {
		startTime = System.currentTimeMillis();
	}
	
	public static void Update () {
		//Perform AI Logic here
		/*if(endTime + startTime > System.currentTimeMillis()){
			SetSwervePower(0,1,0);
		}
		else SetSwervePower(0,0,0);*/
		
		//Update values
		
		//SetSwervePower(0, 1, 0); //Move forward
	}
	
	
	//Utility methods
	
	static void SetSwervePower (double x, double y, double z) { //Convenience method
		//Set the X axis
		Actuator.downPosition();
		boolean fieldOrientation = false;
		double transX =  0;
		double transY = .5;
		double rotation = 0 ;
		if (!fieldOrientation) {
			if ((transX != 0 || transY != 0) && rotation == 0) {
				rotation = Gyro.GetAngle() * -.08;
			} else {
				Gyro.Reset();
			}
		}
		Swerve.Drive(transX, transY, rotation, fieldOrientation);

	}
	
	public void SetPanzerPower (double x, double y) { //Convenience method
		//Set the X axis
		SetAxis(x, 0, 1);
		//Set the Y axis
		SetAxis(y, 1, 1);
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