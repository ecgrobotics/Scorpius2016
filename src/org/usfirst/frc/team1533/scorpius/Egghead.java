package org.usfirst.frc.team1533.scorpius;

//Handles all controls in autonomous mode
public class Egghead {
	
	public static void Initialize () {
		
	}
	
	public static void Update () {
		//Perform AI Logic here
		
		//Update values
		SetSwervePower(0, 1, 0); //Move forward
	}
	
	
	//Utility methods
	
	static void SetSwervePower (double x, double y, double z) { //Convenience method
		//Set the X axis
		SetAxis(x, 0, 0);
		//Set the Y axis
		SetAxis(y, 1, 0);
		//Set the Z axis
		SetAxis(z, 2, 0);
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