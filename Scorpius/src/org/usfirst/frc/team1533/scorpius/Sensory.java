package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.Joystick;

//All input must pass through here
public class Sensory {
	
	//State vars
	static Joystick pad0;
	static Joystick pad1;
	
	//Internal
	
	public static void Initialize () {
		//Initialize joysticks
		pad0 = new Joystick(0);
		pad1 = new Joystick(1);
	}
	
	public static void Update () {
		//Update Swerve input values
		swerve.x = pad0.getRawButton(1) ? 0 : pad0.getX();
		swerve.y = -pad0.getY(); //UPDATE //Apply smoothing and scaling here
		swerve.z = pad0.getRawButton(2) ? pad0.getZ()/4 : pad0.getZ(); //UPDATE //Apply smoothing and scaling here
		//Update Panzer input values
		panzer.left= pad1.getX();
		panzer.right= pad1.getRawAxis(3);
		//Handle button events
		ProcessButtons();

	}
	
	public static boolean Gamepad0Driving () {
		return Math.abs(pad0.getY()) > ConstantFactory.Sensory.GAMEPAD_MOVING_THRESHOLD || Math.abs(pad0.getX()) > ConstantFactory.Sensory.GAMEPAD_MOVING_THRESHOLD;
	}
	
	private static void ProcessButtons () {
		//Iterate through the gamepad searching for button presses
		for (int i = 1; i < ConstantFactory.Sensory.BUTTON_SEARCH_MAPPING_ID_MAX; i++) {
			//Check for a button press
			if (pad0.getRawButton(i)) PropagateButtonDown(ButtonMapping.Button(i), 0);
			if (pad1.getRawButton(i)) PropagateButtonDown(ButtonMapping.Button(i), 1);
		}
	}
	
	private static void PropagateButtonDown (ButtonMapping button, int gamepad) {
		//Notify listeners of button press
		Actuator.OnButtonDown(button, gamepad);
		Swerve.OnButtonDown(button, gamepad);
	}
	
	
	//Accessors
	
	public static class swerve {
		public static double x;
		public static double y;
		public static double z;
	}
	public static class panzer {
		public static double left;
		public static double right;
	}
	public static class actuator {
		public static double p;
	}
}
