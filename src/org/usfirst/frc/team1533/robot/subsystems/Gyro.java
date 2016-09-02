package org.usfirst.frc.team1533.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro {
	public static ADXRS450_Gyro gyro;
	double currentangle;
	
	public Gyro(){
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();
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