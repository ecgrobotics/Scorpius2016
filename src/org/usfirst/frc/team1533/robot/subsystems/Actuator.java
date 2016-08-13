package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Actuator implements PIDOutput {
	SpeedController actuator;
	AnalogInput encoder;
	Joystick joy;
	PIDController pid;
	Vision vision;
	double speed = 0, offset;

	public Actuator(Joystick joy, Vision vision){
		this.joy = joy;
		this.vision = vision;

		actuator = new Spark(ConstantFactory.ACTUATOR);
		encoder = new AnalogInput(ConstantFactory.ACTUATOR_ENCODER);
		pid = new PIDController(1, 1, 1, encoder, actuator);
		pid.setInputRange(0, 5);
	}

	public void move(){
		SmartDashboard.putNumber("actuatorvoltage", encoder.getAverageVoltage());
		offset = SmartDashboard.getNumber("offsetvoltage", 0);


		if(joy.getPOV() == 0){
			pid.disable();
			actuator.set(lerp(1));
		}
		else if(joy.getPOV() == 180){
			pid.disable();
			actuator.set(lerp(-1));
		}
		else if(joy.getPOV() == 90){
			pid.enable();
			pid.setSetpoint(3.5);
		}
		else if(joy.getPOV() == 270){
			pid.enable();
			pid.setSetpoint(vision.vertical() + offset);
		}
		else{
			pid.disable();
			actuator.set(lerp(0));
		}
	}



	public void autonomous(double voltage){
		pid.enable();
		pid.setSetpoint(voltage);
	}
	public double lerp(double b){
		speed = (b-speed)*4.8*.033 + speed;
		return speed;
	}
	public double getSetpoint(){
		return pid.getSetpoint();
	}
	public double getAverageVoltage(){
		double voltage = encoder.getAverageVoltage();
		return voltage;
	}
	public void set(double speed){
		pid.disable();
		actuator.set(speed);
	}
	@Override
	public void pidWrite(double output) {
		if(Math.abs(speed) < Math.abs(output)){
			actuator.set(lerp(output));
		}else actuator.set(output);
	}
}