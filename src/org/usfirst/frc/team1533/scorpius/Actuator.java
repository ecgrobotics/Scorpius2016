package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Actuator {
	
	//State vars
	static SpeedController actuator;
	static ActuatorEncoder encoder; //UPDATE //Might be different interface
	private int kp, kd, ki;
	static PIDOutput output;
	static PIDSource source;
	static boolean linearControl = true;
	//static PIDController controller = new PIDController(kp, kd, ki, source, output, .05);

	
	static double current = 0;
	static double target = 0;
	
	final static double angleVoltage = 3.65, bottomVoltage = 1.5, initVoltage = 3.1;
	
	public static void Initialize () {
		actuator = new Spark(ConstantFactory.RobotMap.ACTUATOR);
		encoder = new ActuatorEncoder(ConstantFactory.RobotMap.ACTUATOR_ENCODER);
	}
	public static void initPosition(){
		if ((encoder.getAverageVoltage()+.05) >= initVoltage) target = -1;
		else if((encoder.getAverageVoltage() -.05)<= initVoltage) target = 1;
		else target = 0;
	}


	public static void shootPosition(){
		if ((encoder.getAverageVoltage()-.05) <= angleVoltage) target = 1;
		else if((encoder.getAverageVoltage() +.05)>= angleVoltage) target = -1;
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
		else if(Sensory.pad1.getPOV(0)  == 90) target = Math.max(-1, Math.min((angleVoltage-encoder.getAverageVoltage())*1, 1));
		else if(Sensory.pad1.getPOV(0)==270) target = Math.max(-1, Math.min((bottomVoltage-encoder.getAverageVoltage())*1, 1));
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
}
