package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.Joystick;

//All input must pass through here
public class Sensory {
	
	//Private State vars
	public static Joystick pad0;
	public static Joystick pad1;
	
	//Public vars
	static boolean isHybridEnabled, swerveTank = false;
	
	//Public //Allow Autonomous override
	public static double[][] axes = new double[2][];
	public static boolean[][] buttons = new boolean[2][];
	public static int[][] povs = new int[2][];
	
	
	public static void Initialize () {
		//Initialize joysticks
		pad0 = new Joystick(0);
		pad1 = new Joystick(1);
		//Initialize buttons
		buttons[0] = new boolean[ConstantFactory.Sensory.BUTTON_SEARCH_MAPPING_ID_MAX];
		buttons[1] = new boolean[ConstantFactory.Sensory.BUTTON_SEARCH_MAPPING_ID_MAX];
		//Initialize axes
		axes[0] = new double[ConstantFactory.Sensory.AXIS_SEARCH_MAPPING_ID_MAX];
		axes[1] = new double[ConstantFactory.Sensory.AXIS_SEARCH_MAPPING_ID_MAX];
		//Initialize POVs
		povs[0] = new int[pad0.getPOVCount()];
		povs[1] = new int[pad1.getPOVCount()];
		
	}
	
	public static void Update () {
		//Iterate through gamepads
		for (int g = 0; g < axes.length; g++) {
			//Iterate through the axes for each gamepad
			for (int a = 0; a < axes[g].length; a++) {
				//Get the value of the axis
				axes[g][a] = (g == 0 ? pad0 : pad1).getRawAxis(a);
			}
			//Iterate through buttons for each gamepad
			for (int b = 0; b < buttons[g].length; b++) {
				//Get the value of the button
				buttons[g][b] = (g == 0 ? pad0 : pad1).getRawButton(b);
			}
			//Iterate through POV's for each gamepad
			for (int p = 0; p < povs[g].length; p++) {
				//Get the value of each point of view
				povs[g][p] = (g == 0 ? pad0 : pad1).getPOV(p);
			}
		}
	}
	
	public static double GetAxis (int axis, int gamepad) {
		return Math.abs(axes[gamepad][axis]) > ConstantFactory.Sensory.AXIS_SENSITIVITY_THRESHOLD ? axes[gamepad][axis] : 0; //UPDATE //ConstantFactory
	}
	
	public static boolean GetButtonDown (ButtonMapping button, int gamepad) {
		return buttons[gamepad][button.GetMappingID()];
		//return (gamepad==0 ? pad0 : pad1).getRawButton(button.GetMappingID()); //I need the abstraction for autonomous override :/
	}
	
//	public static int GetPOV (int index, int gamepad) {
//		return povs[gamepad][index >= povs[gamepad].length ? povs[gamepad].length - 1 : index]; //Automatically clamp value so we don't get OutOfBoundsException
//	}
//	
	public static boolean tankOverride() { //INCOMPLETE //Autonomous override
		if(GetButtonDown(ButtonMapping.LEFT_TRIGGER, 0))	swerveTank = true;
		else if(!GetButtonDown(ButtonMapping.LEFT_TRIGGER, 0)) swerveTank = false;
		return swerveTank;
	}

}
