package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.AnalogInput;

//Implemented from AbsoluteEncoder, credit to Duncan
public class ActuatorEncoder extends AnalogInput  {
		
	//Constants
	final double voltageMin = 0.2; //UPDATE
	final double voltageMax = 4.8; //UPDATE
	final double intersectionAngle = 23.0; //UPDATE
	final double pivotLength = 7.0; //UPDATE //inches?
	final double maxExtensionLength = 4.0; //UPDATE //inches?
	final double slantBaseOffset = 0.8; //UPDATE //inches?
	
	public ActuatorEncoder(int channel) {
		super(channel);
	}
	
	public double getAngle() { //DEPLOY
		//Get the voltage
		double voltage = getVoltage();
		//Calculate the length of the actuator
		double extent = (voltage * (voltageMax - voltageMin) + voltageMin) * maxExtensionLength - slantBaseOffset;
		//Calculate the angle that the arm makes with the horizontal (in radians)
		double angle = Math.asin(extent * Math.sin(Math.toRadians(intersectionAngle)) / pivotLength); //INCOMPLETE
		//Return the angle in degrees
		return Math.toDegrees(angle);
	}
	
	@Override
	public double pidGet () {
		return getAngle();
	}
}
