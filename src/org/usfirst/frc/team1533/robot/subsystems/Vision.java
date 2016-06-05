package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {
	NetworkTable table;
	Joystick joy1, joy2;
	double[] defaultValue;
	int index;

	public Vision(Joystick joy1, Joystick joy2){
		this.joy1 = joy1;
		this.joy2 = joy2;
		table = NetworkTable.getTable("GRIP/myContoursReport");
		defaultValue = new double[0];
	}
	public void process(){
		if(joy1.getRawButton(ConstantFactory.A)){
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
		double setpoint = 0;
		return setpoint;
	}
	public double horizontal(){
		double rotate = 0;
		return rotate;
	}
}
