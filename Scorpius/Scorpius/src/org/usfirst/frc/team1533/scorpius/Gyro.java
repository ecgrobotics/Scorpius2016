package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Gyro {
	
	static ADXRS450_Gyro gyro;
	
	public static void Initialize () {
		gyro = new ADXRS450_Gyro();
		gyro.calibrate();
	}
	
	public static double GetAngle() {
		return gyro.getAngle();
	}
	
	public static void Reset () {
		gyro.reset();
	}
}
