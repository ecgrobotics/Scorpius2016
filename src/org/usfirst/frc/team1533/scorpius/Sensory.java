package org.usfirst.frc.team1533.scorpius;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

//All input must pass through here
public class Sensory {
	
	//State vars
	static Joystick pad0;
	static Joystick pad1;
	static boolean isHybridEnabled;
	
	//Internal
	static boolean[] buttonsPressed = new boolean[ConstantFactory.Sensory.BUTTON_SEARCH_MAPPING_ID_MAX * 2];
	
	public static void Initialize () {
		//Initialize joysticks
		pad0 = new Joystick(0);
		pad1 = new Joystick(1);
	}
	
	public static void Update () {
		//Iterate through buttons
		for (int i = 0; i < buttonsPressed.length; i++) {
			//Set everything to false
			buttonsPressed[i] = false;
			//Check for a button press
			buttonsPressed[i] = i >= ConstantFactory.Sensory.BUTTON_SEARCH_MAPPING_ID_MAX ? pad1.getRawButton(i - ConstantFactory.Sensory.BUTTON_SEARCH_MAPPING_ID_MAX + 1) : pad0.getRawButton(i+1);
		}
	}
		
	public static double GetAxis (AxisType axis, int gamepad) {
		double ax = gamepad == 0 ? pad0.getAxis(axis) : pad1.getAxis(axis);
		if (Math.abs(ax) < .05) return 0;
		return ax;
	}
	
	public static double GetAxis (int axis, int gamepad) {
		double ax = gamepad == 0 ? pad0.getRawAxis(axis) : pad1.getRawAxis(axis);
		if (Math.abs(ax) < .05) return 0;
		return ax;
	}
	
	public static boolean GetButtonDown (ButtonMapping button, int gamepad) { //DEPLOY //+1 offset
		return (gamepad==0 ? pad0 : pad1).getRawButton(button.GetMappingID());
//		return buttonsPressed[button.GetMappingID()+ (gamepad == 0 ? 0 : ConstantFactory.Sensory.BUTTON_SEARCH_MAPPING_ID_MAX)];
	}
	public static double getDPad (int axis, int gamepad){
		return gamepad == 0 ? pad0.getRawAxis(axis) : pad1.getRawAxis(axis);
	}
	public static boolean tankOverride() {
		return /*Math.abs(pad0.getY()) < ConstantFactory.Sensory.TANK_OVERRIDE_THRESHOLD && Math.abs(pad0.getX()) < ConstantFactory.Sensory.TANK_OVERRIDE_THRESHOLD &&*/ GetButtonDown(ButtonMapping.LEFT_TRIGGER, 0);
	}

}
