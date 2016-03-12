package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro {
	
	static ADXRS450_Gyro gyro;
	
	public static void Initialize () {
<<<<<<< HEAD
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
=======
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0); //UPDATE
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
		gyro.calibrate();
	}
	
	public static void Update () {
		SmartDashboard.putNumber("Gyro", GetAngle());
	}
	
	public static double GetAngle() {
		return gyro.getAngle() % 360.0;
	}
	
	public static void Reset () {
		gyro.reset();
	}
}
