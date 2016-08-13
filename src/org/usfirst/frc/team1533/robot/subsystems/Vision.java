package org.usfirst.frc.team1533.robot.subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	NetworkTable table;
	double[] defaultValue;
	double[][] nt;
	int index;
	int recentindex;
	double[] recentY, recentX, areas;


	public Vision(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
		defaultValue = new double[0];
	}

	public void process(){
		areas = table.getNumberArray("area", defaultValue);
		//filter for max contour
		double max = 0;
		for(int i = 0; i < areas.length; i++)
			if(areas[i] > max){
				max = areas[i];
				index = i;
			}
		if(areas[index] > 691)
			recentindex = index;
		recentY = table.getNumberArray("centerY", defaultValue);
		recentX = table.getNumberArray("centerX", defaultValue);
	}


	public double vertical(){
		double[] y = table.getNumberArray("centerY", defaultValue);
		try {
			SmartDashboard.putNumber("yvalue", y[index]);
			double setpoint = 0 - (1.025*y[index]) + (.01314*Math.pow(y[index], 2)) -(8.234 * Math.pow(10, -5)*Math.pow(y[index], 3)) + (2.513*Math.pow(10, -7)*Math.pow(y[index], 4)) - (2.976*Math.pow(10, -9)*Math.pow(y[index], 5));
			SmartDashboard.putNumber("setpoint", setpoint);
			return setpoint;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public double horizontal(){
		double[] x = table.getNumberArray("centerX", defaultValue);
		double offset = SmartDashboard.getNumber("chassisoffset", 0);
		try {
			double rotate = (x[index]-160-24+offset)/320d*50*10.01;
			return rotate;
		} catch (Exception e) {
			return 0;
		}
	}
}
