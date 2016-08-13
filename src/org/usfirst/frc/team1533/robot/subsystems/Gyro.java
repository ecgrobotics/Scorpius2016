package org.usfirst.frc.team1533.robot.subsystems;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class Gyro {
	public ADXRS450_Gyro gyro;
	public ADXL362 acc;
	public double currentangle;
	
	public Gyro(){
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();
		acc = new ADXL362(SPI.Port.kOnboardCS1, Range.k8G);
	}
	public double getAngle() {
		return gyro.getAngle();
	}
	
	public double getRate() {
		return gyro.getRate();
	}
	
	public double angleCorrect(){
		return gyro.getAngle() * -.015;
	}
	
	public void reset(){
		gyro.reset();
	}
	
	public double straight(boolean angle){
		if(angle){
			currentangle = gyro.getAngle();
			Swerve.angle = false;
		}
		return	(gyro.getAngle()-currentangle)*.015;
	}

}
