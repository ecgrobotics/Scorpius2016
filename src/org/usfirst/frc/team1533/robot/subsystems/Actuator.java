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
<<<<<<< HEAD
	double speed = 0, offset;

	public Actuator(Joystick joy, Vision vision){
		this.joy = joy;
=======
	static double speed;

	public Actuator(Joystick joy1, Joystick joy2, Vision vision){
<<<<<<< HEAD
>>>>>>> parent of 4f751c5... Update
=======
>>>>>>> parent of 4f751c5... Update
		this.vision = vision;

		actuator = new Spark(ConstantFactory.ACTUATOR);
		encoder = new AnalogInput(ConstantFactory.ACTUATOR_ENCODER);
<<<<<<< HEAD
		pid = new PIDController(1, 1, 1, encoder, actuator);
		pid.setInputRange(0, 5);
	}

	public void move(){
		SmartDashboard.putNumber("actuatorvoltage", encoder.getAverageVoltage());
		offset = SmartDashboard.getNumber("offsetvoltage", 0);


		if(joy.getPOV() == 0){
=======
		pid = new PIDController(1, 1, 1, encoder, this);
		this.joy1 = joy1;
		this.joy2 = joy2;
		speed = 0;

		
		
		pid.setInputRange(.2, 5);
		pid.disable();
	}

	public void move(){
		if(joy2.getPOV() == 0){
>>>>>>> parent of 4f751c5... Update
			pid.disable();
			actuator.set(lerp(1));
		}
		else if(joy.getPOV() == 180){
			pid.disable();
			actuator.set(lerp(-1));
		}
<<<<<<< HEAD
		else if(joy.getPOV() == 90){
			pid.enable();
			pid.setSetpoint(3.5);
		}
		else if(joy.getPOV() == 270){
			pid.enable();
			pid.setSetpoint(vision.vertical() + offset);
=======
		else if(joy2.getPOV() == 90){
			pid.disable();
			actuator.set((3.72 - encoder.getAverageVoltage())*1);
		}
		else if(joy2.getPOV() == 270){
			pid.disable();
			actuator.set((1.5 - encoder.getAverageVoltage())*1);
		}
		else if(joy2.getRawButton(ConstantFactory.X2)){
			pid.disable();
			// goto angle determined by vision
			actuator.set((vision.vertical() - encoder.getAverageVoltage())*1);
<<<<<<< HEAD
>>>>>>> parent of 4f751c5... Update
=======
>>>>>>> parent of 4f751c5... Update
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