package org.usfirst.frc.team1533.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;

public class Gyro {
	ADXRS450_Gyro gyro;
	
	public Gyro(){
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();
	}

	public double getAngle() {
		return gyro.getAngle();
	}

}
