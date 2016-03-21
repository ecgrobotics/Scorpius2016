package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro {
	
	static ADXRS450_Gyro gyro;
	
	
	public static void Initialize () {
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();
	}
	
	public static void Update () {
		SmartDashboard.putNumber("Gyro", GetAngle());
	}
	
	public static double GetAngle() {
		return gyro.getAngle();
	}
	
	public static void Reset () {
		gyro.reset();
	}
	public static double GetRate(){
		return gyro.getRate();
	}

}
