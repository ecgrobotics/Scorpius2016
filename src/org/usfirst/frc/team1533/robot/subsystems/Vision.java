package org.usfirst.frc.team1533.robot.subsystems;

import java.util.Arrays;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	NetworkTable table;
	Joystick joy1, joy2;
	double[] defaultValue;
	double[][] nt;
	int index;
	boolean toggle, p;
	int recentindex;
	double[] recentY, recentX, areas;
	

	public Vision(){
		toggle = false;
		p = true;
		table = NetworkTable.getTable("GRIP/myContoursReport");
		defaultValue = new double[0];
	}

	public void process(){
		if(p){
			areas = table.getNumberArray("area", defaultValue);

			double max = 0;
			//find index for goal aiming for if more than one
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
	}

	public double vertical(){
		double[] y = table.getNumberArray("centerY", defaultValue);
		//random operation to transform y to actuator val
		try {
			SmartDashboard.putNumber("yvalue", y[index]);

			
			double setpoint = 3.962692 - (.0246*y[index]) + (.0002328*Math.pow(y[index], 2)) - (0.00000111*Math.pow(y[index], 3)) + (.00000000201*Math.pow(y[index], 4));
			return setpoint;
			
		} catch (Exception e) {
			return 0;
		}
	}
	public double horizontal(){
		double[] x = table.getNumberArray("centerX", defaultValue);
		//rand operation to transform x to rotation setpoint
		try {
			double rotate = (x[index]-160-24)/320d*50*10.01;
			return rotate;
		} catch (Exception e) {
			return 0;
		}
	}
}