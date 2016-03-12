package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.PIDController;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
=======
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Actuator {
	
	//State vars
<<<<<<< HEAD
	static SpeedController actuator;
	private static ActuatorEncoder encoder; //UPDATE //Might be different interface
	private int kp, kd, ki;
	static PIDOutput output;
	static PIDSource source;
	static boolean linearControl = true;
	//static PIDController controller = new PIDController(kp, kd, ki, source, output, .05);

=======
	private static PIDController controller;
	static SpeedController actuator;
	private static ActuatorEncoder encoder; //UPDATE //Might be different interface
	static double angle = 30.0;
	private static Extensions.Callback completionHandler;
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
	
	static double current = 0;
	static double target = 0;
	
<<<<<<< HEAD
	final static double angleVoltage = 4.3, bottomVoltage = .5, initVoltage = 3.1;
	
	public static void Initialize () {
		actuator = new Spark(ConstantFactory.RobotMap.ACTUATOR);
		encoder = new ActuatorEncoder(ConstantFactory.RobotMap.ACTUATOR_ENCODER);
	}
	public static void initPosition(){
		if ((encoder.getAverageVoltage()+.05) >= initVoltage) target = -1;
		else if((encoder.getAverageVoltage() -.05)<= initVoltage) target = 1;
		else target = 0;
	}
	public static void downPosition(){
		if ((encoder.getAverageVoltage()-.05) <= bottomVoltage) target = 1;
		else if((encoder.getAverageVoltage() +.05)>= bottomVoltage) target = -1;
		else target = 0;
	}

	public static void Update () { //DEPLOY
		

		if(Sensory.pad0.getPOV(0) == 90 || Sensory.pad0.getPOV(0) == 180){
			linearControl = false;
		}
		else if(Sensory.pad1.getPOV(0) == 90 || Sensory.pad1.getPOV(0) == 180){
			linearControl = true;
		}
		target = 0;
		if(Sensory.GetButtonDown(ButtonMapping.LEFT_BUMPER, 1 )){
			current = 1;
		}
		if(Sensory.GetButtonDown(ButtonMapping.LEFT_TRIGGER,1)){
			current = -1;
		}
		else{
		if(linearControl){
		if(Sensory.pad1.getPOV(0) == 0) target = 1;
		else if(Sensory.pad1.getPOV(0)  == 180) target = -1;
		else if(Sensory.pad1.getPOV(0)  == 90) {
				if ((encoder.getAverageVoltage()+.2) >= angleVoltage) target = -1;
				else if((encoder.getAverageVoltage() -.2)<= angleVoltage) target = 1;
				else target = 0;
					}
		else if(Sensory.pad1.getPOV(0)==270) {
			
				if ((encoder.getAverageVoltage()-.2) <= bottomVoltage) target = 1;
				else if((encoder.getAverageVoltage() +.2)>= bottomVoltage) target = -1;
				else target = 0;
					}
		else target = 0;
		}
		else if(linearControl == false){
			if(Sensory.pad0.getPOV(0) == 0) target = 1;
			else if(Sensory.pad0.getPOV(0)  == 180) target = -1;
			else if(Sensory.pad0.getPOV(0)  == 90) {
					if ((encoder.getAverageVoltage()+.2) >= angleVoltage) target = -1;
					else if((encoder.getAverageVoltage() -.2)<= angleVoltage) target = 1;
					else target = 0;
						}
			else if(Sensory.pad0.getPOV(0)==270) {
				
					if ((encoder.getAverageVoltage()-.2) <= bottomVoltage) target = 1;
					else if((encoder.getAverageVoltage() +.2)>= bottomVoltage) target = -1;
					else target = 0;
						}
			else target = 0;
		}
    	current = Extensions.Lerp (current, target, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
		}
		
		actuator.set(current);

        SmartDashboard.putNumber("Actuator percent", current);
        SmartDashboard.putNumber("Actuator target", target);
        SmartDashboard.putNumber("Actuator encoder voltage", encoder.getAverageVoltage());
        SmartDashboard.putNumber("Actuator up", angleVoltage);
        SmartDashboard.putNumber("Actuator  down", bottomVoltage);
        
        //TODO Make target encoder values variable

		
	}
=======
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
		if(Sensory.GetPOV(0, 1) == 0) target = 1;
		else if(Sensory.GetPOV(0, 1) == 180) target = -1;
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
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
}
