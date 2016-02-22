package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Actuator {
	
	//State vars
	private static PIDController controller;
	private static SpeedController actuator;
	private static ActuatorEncoder encoder; //UPDATE //Might be different interface
	private static double angle = 30.0;
	private static double target;
	private static Callback completionHandler;
	
	private static double current = 0; //DEBUG
	public static double scalarActuator = .2;
	public static void Initialize () {
		actuator = new Spark(ConstantFactory.RobotMap.ACTUATOR);
		/*
		encoder = new ActuatorEncoder(ConstantFactory.RobotMap.ACTUATOR_ENCODER);
		controller = new PIDController(ConstantFactory.Actuator.P, ConstantFactory.Actuator.I, ConstantFactory.Actuator.D, encoder, actuator);
		//Set PID parameters
		controller.setInputRange(ConstantFactory.Actuator.ANGLE_MINIMUM, ConstantFactory.Actuator.ANGLE_MAXIMUM);
		controller.setOutputRange(-ConstantFactory.Actuator.POWER_MAGNITUDE_MAXIMUM, ConstantFactory.Actuator.POWER_MAGNITUDE_MAXIMUM);
		controller.setContinuous();
		controller.setSetpoint(angle);
		controller.enable();
		*/
	}
	
	public static void Update () { //DEPLOY
		//DEBUG
		/*
		 * Uses left bumper and trigger for slow actuator mode
		 * Uses right bumper and trigger for maximum actuator speed
        */
   
        
        //Interpolate current
        current = Extensions.Lerp(current, target, 5*0.033);
        //Set the actuator
        actuator.set(current);
        
		//SmartDashboard.putNumber("Encoder voltage", encoder.getVoltage());
		
		/*
		//Apply it
		controller.setSetpoint(angle);
		//Check for any completion handlers
		if (Math.abs(controller.getSetpoint() - angle) < ConstantFactory.Actuator.ANGLE_EQUALITY_THRESHOLD && completionHandler != null) {
			completionHandler.Execute();
			completionHandler = null;
		}
		*/
	}
	
	public static boolean SetAngle (double Angle, Callback callback) {
		//Op checking
		if (completionHandler != null) return false;
		//Set state vars
		angle = Angle;
		completionHandler = callback;
		//Return
		return true;
	}
	
	public static void OnButtonDown (ButtonMapping button, int gamepad) {
		//Gamepad 1 controls the actuator
		if (gamepad == 1) {
			
			//Reset forward orientation
			if (button.equals(ButtonMapping.L1)) {
				current = 0.2;
			}
			//Toggle field orientation
			else if (button.equals(ButtonMapping.R2)) {
				current = 1;
			}
			else if(button.equals(ButtonMapping.R1)){
				current = -1;
			}
			else if(button.equals(ButtonMapping.L2)){
				current = -.2;
			}
			else{
				current = 0;
			}
		}
	}
}
