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
	Joystick joy1, joy2;
	PIDController pid;
	Vision vision;
	static double speed;
	Stinger stinger;

	public Actuator(Joystick joy1, Joystick joy2, Vision vision, Stinger stinger){
		this.vision = vision;
		actuator = new Spark(ConstantFactory.ACTUATOR);
		encoder = new AnalogInput(ConstantFactory.ACTUATOR_ENCODER);
		pid = new PIDController(1, 1, 1, encoder, this);
		this.joy1 = joy1;
		this.joy2 = joy2;
		speed = 0;
		this.stinger = stinger;

		
		
		pid.setInputRange(.2, 5);
		pid.disable();
	}

	public void move(){
		SmartDashboard.putNumber("vertical", vision.vertical());
		if(joy2.getPOV() == 0){
			pid.disable();
			actuator.set(lerp(1));
		}
		else if(joy2.getPOV() == 180){
			pid.disable();
			actuator.set(lerp(-1));
		}
		else if(joy2.getPOV() == 90){
			pid.disable();
			actuator.set((3.72 - encoder.getAverageVoltage())*1);
		}
		else if(joy2.getPOV() == 270){
			pid.disable();
			actuator.set((vision.vertical() - encoder.getAverageVoltage())*1);
		}
		else if(joy2.getRawButton(ConstantFactory.X2)){
			pid.disable();
			// goto angle determined by vision
			actuator.set(((vision.vertical() +1) - encoder.getAverageVoltage())*2);
		}
		else{
			pid.disable();
			actuator.set(lerp(0));
		}
//		dashboard();
		SmartDashboard.putNumber("voltage", getAverageVoltage());
	}
	public void autonomous(double voltage){
		actuator.set((voltage - encoder.getAverageVoltage())*2);
	}
	public void dashboard(){
//		SmartDashboard.putNumber("Arm Angle", encoder.getAverageVoltage());
//		SmartDashboard.putNumber("Voltage", encoder.getAverageVoltage());
	}
	public static double lerp(double b){
		speed = (b-speed)*4.8*.033 + speed;
		return speed;
	}

	public double getSetpoint(){
		return pid.getSetpoint();
	}
	public void moveTo(double voltage){
		if(!pid.isEnabled()) pid.enable();  
		pid.setSetpoint(voltage);
	}
	public double getAverageVoltage(){
		double voltage = encoder.getAverageVoltage();
		return voltage;
	}
	public void set(double speed){
		actuator.set(speed);
	}
	@Override
	public void pidWrite(double output) {
		if(Math.abs(speed) < Math.abs(output)){
			actuator.set(lerp(output));
		}else actuator.set(output);
	}
}