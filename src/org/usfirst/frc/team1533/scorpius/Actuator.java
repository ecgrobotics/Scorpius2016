package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Actuator {
	
	//State vars
	private static PIDController controller;
	static SpeedController actuator;
	private static ActuatorEncoder encoder; //UPDATE //Might be different interface
	static double angle = 30.0;
	private static Extensions.Callback completionHandler;
	
	static double current = 0;
	static double target = 0;
	
	public static void Initialize () {
		actuator = new Spark(ConstantFactory.RobotMap.ACTUATOR);
		encoder = new ActuatorEncoder(ConstantFactory.RobotMap.ACTUATOR_ENCODER);
		controller = new PIDController(ConstantFactory.Actuator.P, ConstantFactory.Actuator.I, ConstantFactory.Actuator.D, encoder, actuator);
		//Set PID parameters
		controller.setInputRange(ConstantFactory.Actuator.ANGLE_MINIMUM, ConstantFactory.Actuator.ANGLE_MAXIMUM);
		controller.setOutputRange(-ConstantFactory.Actuator.POWER_MAGNITUDE_MAXIMUM, ConstantFactory.Actuator.POWER_MAGNITUDE_MAXIMUM);
		//initialize angle to current angle
		angle = encoder.getAngle();
		controller.setSetpoint(angle);
//		controller.enable();
	}
	
	public static void Update () { //DEPLOY
		//Left bumper and trigger on gamepad 1 to move up and down respectively
		/*target = Sensory.GetButtonDown(ButtonMapping.LEFT_BUMPER, 1) ? 1 : Sensory.GetButtonDown(ButtonMapping.LEFT_TRIGGER, 1) ? -1 : 0;
    	current = Extensions.Lerp (current, target, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
        actuator.set(current);
        */

        
        //DEBUG
		//SmartDashboard.putNumber("Encoder voltage", encoder.getVoltage());
		
		
		//Apply it
		//OLD CODE THAT DUNCAN COMMENTED OUT TO FIX STUFF AT THE LAST MINUTE
//		if(Sensory.pad1.getPOV(0) == 0) target = 1;
//		else if(Sensory.pad1.getPOV(0) == 180) target = -1;
//		else if(Sensory.GetButtonDown(ButtonMapping.LEFT_BUMPER, 1)) target = .25;
//		else if(Sensory.GetButtonDown(ButtonMapping.LEFT_TRIGGER, 1)) target = -.25;
//		else target = -current/(ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033) +current;
//    	current = Extensions.Lerp (current, target, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
//        actuator.set(current);
//		controller.setSetpoint(angle);
//		//Check for any completion handlers
//		if (Math.abs(controller.getSetpoint() - angle) < ConstantFactory.Actuator.ANGLE_EQUALITY_THRESHOLD && completionHandler != null) {
//			completionHandler.Execute();
//			completionHandler = null;
//		}
//        SmartDashboard.putNumber("Actuator percent", current);
//        SmartDashboard.putNumber("Actuator target", target);
		
		target = 0;
		if(Sensory.pad1.getPOV() == 0) target = 1;
		else if(Sensory.pad1.getPOV() == 180) target = -1;
    	current = Extensions.Lerp (current, target, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
		actuator.set(current);
//		angle += target * .1;
//		controller.setSetpoint(angle);
		//Check for any completion handlers
		if (Math.abs(controller.getSetpoint() - angle) < ConstantFactory.Actuator.ANGLE_EQUALITY_THRESHOLD && completionHandler != null) {
			completionHandler.Execute();
			completionHandler = null;
		}
        SmartDashboard.putNumber("Actuator percent", current);
        SmartDashboard.putNumber("Actuator target", target);
        SmartDashboard.putNumber("Actuator encoder voltage", encoder.getAverageVoltage());
        SmartDashboard.putNumber("Actuator calculated angle", encoder.getAngle());
		
	}
	
	public static boolean SetAngle (double Angle, Extensions.Callback callback) {
		//Op checking
		if (completionHandler != null) return false;
		//Set state vars
		angle = Angle;
		completionHandler = callback;
		//Return
		return true;
	}
}
