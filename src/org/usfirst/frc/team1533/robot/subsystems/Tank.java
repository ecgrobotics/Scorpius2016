package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Tank {
	RobotDrive drive;
	Joystick joy;
	double speedX, speedY;


	public Tank(Joystick joy){
		this.joy = joy;
		drive = new RobotDrive(ConstantFactory.L_TREAD, ConstantFactory.R_TREAD);
		speedX = 0;
		speedY = 0;
	}

	public void move(){

		double k = 1;
		double targetX = joy.getRawAxis(2) > 0.05 ? 1 : joy.getRawAxis(2) < -0.05 ? -1 : 0;
		double targetY = joy.getRawAxis(1) > 0.05 ? 1 : joy.getRawAxis(1) < -0.05 ? -1 : 0;
		double cX = -1+(.01*targetX);
		double cY = -1+(.01*targetY);

		if(joy.getRawButton(ConstantFactory.LEFT_TRIGGER)){
			if(targetX != 0)speedX = ((2)/(1 + Math.pow(10, (-k * speedX)))) + cX;
			else if(targetX == 0) speedX = 0;
			
			if(targetY != 0) speedY = ((2)/(1 + Math.pow(10, (-k * speedY)))) + cY;
			else if(targetY == 0) speedY = 0;
		
		}else if(joy.getRawButton(ConstantFactory.RIGHT_TRIGGER)){
			if(targetX != 0) speedX = ((2)/(1 + Math.pow(10, (-1 * speedX)))) + cX;
			else if(targetX == 0) speedX = 0;
			
			speedY = 0;
		}
		
		drive.arcadeDrive(Math.max(-1,Math.min(speedY,1)), Math.max(-1,Math.min(speedX,1)), false);
	}
	public void autonomous(double x, double y){
		drive.arcadeDrive(x, y);
	}
}
