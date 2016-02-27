package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Implemented from AbsoluteEncoder, credit to Duncan
public class ActuatorEncoder extends AnalogInput  {
		
	//Constants
	final static double voltageMin = 0.2; //UPDATE
	final static double voltageMax = 4.8; //UPDATE
	final static double voltageRange = 4.6;
	final static double intersectionAngle = 23.0; //UPDATE
	final static double pivotLength = 7.0; //UPDATE //inches?
	final static double maxExtensionLength = 4.0; //UPDATE //inches?
	final static double slantBaseOffset = 0.8; //UPDATE //inches?
	private boolean moveActuator = false;
	
	public ActuatorEncoder(int channel) {
		super(channel);
	}

	public double getAngle() { //DEPLOY
		//Get the voltage
		double voltage = getVoltage();
		//Calculate the length of the actuator
		double extent = (voltage * (voltageRange) + voltageMin) * maxExtensionLength - slantBaseOffset;
		//Calculate the angle that the arm makes with the horizontal (in radians)
		double angle = Math.acos((Math.pow(ConstantFactory.Actuator.ACTUATOR_STINGER_BASE_LENGTH, 2) + Math.pow(ConstantFactory.Actuator.STINGER_BASE_PIVOT_LENGTH, 2) - Math.pow(extent, 2))/(2*ConstantFactory.Actuator.ACTUATOR_STINGER_BASE_LENGTH*ConstantFactory.Actuator.STINGER_BASE_PIVOT_LENGTH));
		//Log
		SmartDashboard.putNumber("Value of angle", angle);
		//Return the angle in degrees
		return Math.toDegrees(angle); 
	}
	public void Update(){
		if(Sensory.pad1.getPOV(0) == 90) moveActuator =! moveActuator;
		if(moveActuator == true){
			if(getAngle() < Actuator.angle){
				Actuator.current = Extensions.Lerp (Actuator.current, 1, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
		        Actuator.actuator.set(Actuator.current);
			} else if(getAngle() > Actuator.angle){
				Actuator.current = Extensions.Lerp (Actuator.current, -1, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
		        Actuator.actuator.set(Actuator.current);
			} else Actuator.current = Extensions.Lerp (Actuator.current, -Actuator.current/(ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033) +Actuator.current, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
			moveActuator = false;
		}
	}
	@Override
	public double pidGet () {
		return getAngle();
	}
}
