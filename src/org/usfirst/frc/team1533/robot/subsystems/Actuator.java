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
	static double speed;

	public Actuator(Joystick joy1, Joystick joy2){
		actuator = new Spark(ConstantFactory.ACTUATOR);
		encoder = new AnalogInput(ConstantFactory.ACTUATOR_ENCODER);
		pid = new PIDController(1, 1, 1, encoder, this);
		this.joy1 = joy1;
		this.joy2 = joy2;
		speed = 0;
		pid.setPercentTolerance(.05);

		
		
		pid.setInputRange(-.2, 5);
		pid.disable();
	}

	public void move(){
		if(joy2.getPOV() == 0){
			pid.disable();
			actuator.set(lerp(1));
		}
		else if(joy2.getPOV() == 180){
			pid.disable();
			actuator.set(lerp(-1));
		}
		else if(joy2.getPOV() == 90) moveTo(ConstantFactory.Steering.angleVoltage);
		else if(joy2.getPOV() == 270) moveTo(ConstantFactory.Steering.hangVoltage);		
		else if(joy2.getRawButton(ConstantFactory.LEFT_BUMPER)) actuator.set(1);
		else if(joy2.getRawButton(ConstantFactory.LEFT_TRIGGER)) actuator.set(-1);
		else{
			pid.disable();
			actuator.set(lerp(0));
		}
//		dashboard();
		SmartDashboard.putNumber("voltage", getAverageVoltage());
	}
	public void dashboard(){
//		SmartDashboard.putNumber("Arm Angle", encoder.getAverageVoltage());
//		SmartDashboard.putNumber("Voltage", encoder.getVoltage());
	}
	public static double lerp(double b){
		speed = (b-speed)*4.8*.033 + speed;
		return speed;
	}

//	public void smooth(double target){
//		if(pid.isEnabled()) pid.disable();
//		double k = .25;
//		double c = -1+(target*.01);
//		if(target != 0) speed = ((2)/(1 + Math.pow(10, (-k * speed)))) + c;
//		else if(target == 0){
//			target = speed > 0 ? 1 : speed < 0 ? -1 : 0;
//			speed = ((target)/(1 + Math.pow(10, (-9 * speed + 8))));
//		}
//		actuator.set(speed);
//		
////		double current = actuator.get();
////		if(Math.abs(current) <= 0.05){
////			if(!pid.isEnabled()){
////				pid.enable();
////				lastVoltage = encoder.getAverageVoltage(); 
////			}else if(pid.isEnabled()){
////				double currentVoltage = encoder.getAverageVoltage();
////				if(Math.abs(currentVoltage-lastVoltage) < .05) moveTo(lastVoltage);
////			}
////		}
//	}

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
	@Override
	public void pidWrite(double output) {
		if(Math.abs(speed) < Math.abs(output)){
			actuator.set(lerp(output));
		}else actuator.set(output);
	}
}