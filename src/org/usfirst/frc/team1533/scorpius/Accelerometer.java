package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Accelerometer {
	static ADXL362 accelerometer;
	public static void Initialize () {
		accelerometer = new ADXL362(SPI.Port.kOnboardCS0, edu.wpi.first.wpilibj.interfaces.Accelerometer.Range.k2G);
	}
	public static void Update(){
		SmartDashboard.putNumber("Acceleration in Y:", accelerometer.getX()); //x is y lol
	}
	public double getX() {
		// TODO Auto-generated method stub
		return accelerometer.getX();
	}
}
