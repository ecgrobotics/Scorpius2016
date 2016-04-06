package org.usfirst.frc.team1533.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro {
	public static ADXRS450_Gyro gyro;
	
	public Gyro(){
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
	}

	public double getAngle() {
		return gyro.getAngle();
	}

}
