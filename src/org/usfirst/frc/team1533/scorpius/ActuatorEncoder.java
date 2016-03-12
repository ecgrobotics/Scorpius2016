package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
<<<<<<< HEAD
/*		
		
		*/
=======

>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
//Implemented from AbsoluteEncoder, credit to Duncan
public class ActuatorEncoder extends AnalogInput  {
		
	//Constants
	final static double voltageMin = 0.3; //UPDATE
	final static double voltageMax = 4.6; //UPDATE
	final static double intersectionAngle = 23.0; //UPDATE
	final static double pivotLength = 7.0; //UPDATE //inches?
	final static double maxExtensionLength = 12.0; //UPDATE //inches?
	final static double slantBaseOffset = 0.8; //UPDATE //inches?
<<<<<<< HEAD
=======
	final static double angleVoltage = 3;
	private boolean moveActuator = false;
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
	
	public ActuatorEncoder(int channel) {
		super(channel);
	}

	public double getAngle() { //DEPLOY
		//Get the voltage
		double voltage = getAverageVoltage();
		//Calculate the length of the actuator
		double extent = ((voltage - voltageMin) / (voltageMax - voltageMin)) * maxExtensionLength - slantBaseOffset;
		//Calculate the angle that the arm makes with the horizontal (in radians)
		double angle = Math.acos(Math.PI/2-((Math.pow(ConstantFactory.Actuator.ACTUATOR_STINGER_BASE_LENGTH, 2) + Math.pow(ConstantFactory.Actuator.STINGER_BASE_PIVOT_LENGTH, 2) - Math.pow(extent, 2))/(2*ConstantFactory.Actuator.ACTUATOR_STINGER_BASE_LENGTH*ConstantFactory.Actuator.STINGER_BASE_PIVOT_LENGTH)));
		//Log
		SmartDashboard.putNumber("Value of angle", angle);
		//Return the angle in degrees
		return Math.toDegrees(angle); 
	}
<<<<<<< HEAD

=======
	public void Update(){
		if(Sensory.GetPOV(0, 1) == 90) moveActuator =! moveActuator;
		if(moveActuator == true){
			if(getAverageVoltage() < angleVoltage){
				Actuator.current = Extensions.Lerp (Actuator.current, 1, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
		        Actuator.actuator.set(Actuator.current);
			} else if(getAverageVoltage () > angleVoltage){
				Actuator.current = Extensions.Lerp (Actuator.current, -1, ConstantFactory.Actuator.HARDNESS_CONSTANT * 0.033);
		        Actuator.actuator.set(Actuator.current);
			} else{
				moveActuator = false;
				Actuator.target = 0;
			}
		}
		
	}
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
	@Override
	public double pidGet () {
		return getAngle();
	}
}
