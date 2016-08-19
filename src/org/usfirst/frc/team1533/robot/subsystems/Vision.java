package org.usfirst.frc.team1533.robot.subsystems;

import java.util.Arrays;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {
	NetworkTable table;
	Joystick joy1, joy2;
	double[] defaultValue;
	double[][] nt;
	int index;
	boolean toggle, p;

	public Vision(){
		toggle = false;
		p = true;
		table = NetworkTable.getTable("GRIP/myContoursReport");
		defaultValue = new double[0];
	}
	
	public void process(){
		if(joy1.getRawButton(ConstantFactory.A)){
			toggle = true;
		}else {
//			if(toggle) p = !p;
			toggle = false;
		}
		if(p){
			double[] areas = table.getNumberArray("area", defaultValue);
			
			double max = 0;
			//find index for goal aiming for
			for(int i = 0; i < areas.length; i++)
				if(areas[i] > max){
					max = areas[i];
					index = i;
				}
		}
	}
	
	//place holders called in actuator and swerve which 
	public double vertical(){
		double[] y = table.getNumberArray("centerY", defaultValue);
		//random operation to transform y to actuator val
		try {
			double setpoint = y[index]*420;
			return setpoint;
		} catch (Exception e) {
			return 0;
		}
	}
	public double horizontal(){
		double[] x = table.getNumberArray("centerX", defaultValue);
		//rand operation to transform x to rotation setpoint
		try {
			double rotate = (x[index]-160)/320d*50*10.01;
			return rotate;
		} catch (Exception e) {
			return 0;
		}
	}
}