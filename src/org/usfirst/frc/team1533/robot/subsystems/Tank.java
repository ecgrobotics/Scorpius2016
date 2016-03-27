package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Tank {
	RobotDrive drive;
	Joystick joy, joy2;
	double speedX, speedY;


	public Tank(Joystick joy1, Joystick joy2){
		this.joy = joy1;
		this.joy2 = joy2;
		drive = new RobotDrive(ConstantFactory.L_TREAD, ConstantFactory.R_TREAD);
		speedX = 0;
		speedY = 0;
	}

	public void move(){
		if(joy.getRawButton(ConstantFactory.LEFT_TRIGGER)){
			speedX = -joy.getRawAxis(0);
			speedY = joy.getRawAxis(3);
		
		}	else {
			speedY = 0;
			speedX = 0;
		}
		drive.arcadeDrive(Math.max(-1,Math.min(speedY,1)), Math.max(-1,Math.min(speedX,1)), false);
	}
	public void autonomous(double x, double y){
		drive.arcadeDrive(x, y);
	}
}
