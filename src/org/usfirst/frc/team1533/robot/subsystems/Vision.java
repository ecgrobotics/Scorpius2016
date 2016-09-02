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
	double centery;

	public Vision(Joystick joy1, Joystick joy2){
		toggle = false;
		p = true;
		this.joy1 = joy1;
		this.joy2 = joy2;
		table = NetworkTable.getTable("GRIP/myContoursReport");
		defaultValue = new double[0];
		centery = 0;
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
			centery = y[index];
			SmartDashboard.putNumber("Centery", centery);
			double setpoint =  3.113 - (y[index]*.004375) - (3.819 * Math.pow(10, -6)* Math.pow(y[index], 2)) + (9.336 * Math.pow(10, -8)* Math.pow(y[index], 3));
			return setpoint;
		} catch (Exception e) {
			return 0;
		}
	}
	public double horizontal(){
		double[] x = table.getNumberArray("centerX", defaultValue);
		//rand operation to transform x to rotation setpoint
		try {
			double rotate = (x[index]-160-23)/320d*50*10.01;
			return rotate;
		} catch (Exception e) {
			return 0;
		}
	}
}