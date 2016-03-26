package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class Actuator {
	SpeedController actuator;
	AnalogInput encoder;
	Joystick joy;
	PIDController pid;
	double speed, lastVoltage;

	public Actuator(Joystick joy){
		actuator = new Spark(ConstantFactory.ACTUATOR);
		encoder = new AnalogInput(ConstantFactory.ACTUATOR_ENCODER);
		pid = new PIDController(1, .01, 0, encoder, actuator);
		this.joy = joy;
		speed = 0;

		pid.setInputRange(-.2, 2.2);
		pid.setPercentTolerance(5);
		pid.disable();
	}

	public void move(){
		if(joy.getPOV() == 0) smooth(1);
		else if(joy.getPOV() == 180) smooth(-1);
		else if(joy.getPOV() == 90) moveTo(ConstantFactory.Steering.angleVoltage);
		else if(joy.getPOV() == 270) moveTo(ConstantFactory.Steering.hangVoltage);		
		else if(joy.getRawButton(ConstantFactory.RIGHT_BUMPER)) actuator.set(1);
		else if(joy.getRawButton(ConstantFactory.RIGHT_TRIGGER)) actuator.set(-1);
		else smooth(0);

	}

	public void smooth(double target){
		if(pid.isEnabled()) pid.disable();
		double k = 1;
		double c = -1+(target*.01);
		if(target != 0) speed = ((2*target)/(1 + Math.pow(10, (-k * speed)))) + c;
		else if(target == 0){
			target = speed > 0 ? 1 : speed < 0 ? -1 : 0;
			speed = ((target)/(1 + Math.pow(10, (-9 * speed + 8))));
		}
		actuator.set(speed);
		double current = actuator.get();
		if(Math.abs(current) <= 0.05){
			if(!pid.isEnabled()){
				pid.enable();
				lastVoltage = encoder.getAverageVoltage(); 
			}else if(pid.isEnabled()){
				double currentVoltage = encoder.getAverageVoltage();
				if(Math.abs(currentVoltage-lastVoltage) < .05) moveTo(lastVoltage);
			}
		}
	}

	public void moveTo(double voltage){
		pid.enable();  
		pid.setSetpoint(voltage);
	}

	public double getAverageVoltage(){
		double voltage = encoder.getAverageVoltage();
		return voltage;
	}
	public void set(double speed){
		actuator.set(speed);
	}
}