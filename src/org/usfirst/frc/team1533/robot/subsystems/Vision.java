package org.usfirst.frc.team1533.robot.subsystems;

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

	public Vision(Joystick joy1, Joystick joy2){
		toggle = false;
		p = false;
		this.joy1 = joy1;
		this.joy2 = joy2;
		table = NetworkTable.getTable("GRIP/myContoursReport");
		defaultValue = new double[0];
	}
	
	public void process(){
		if(joy1.getRawButton(ConstantFactory.A)){
			toggle = true;
		}else {
			if(toggle) p = !p;
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
		double setpoint = y[index]*420;
		return setpoint;
	}
	public double horizontal(){
		double[] x = table.getNumberArray("centerX", defaultValue);
		//rand operation to transform x to rotation setpoint
		double rotate = x[index]*254;
		return rotate;
	}
}
