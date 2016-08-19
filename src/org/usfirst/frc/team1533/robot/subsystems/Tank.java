package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;

public class Tank {
	Joystick joy;
	double speedX, speedY;
	Swerve swerve;
	Spark left, right;

<<<<<<< HEAD
	public Tank(Joystick joy1, Swerve swerve, Gyro gyro){
=======

	public Tank(Joystick joy1, Joystick joy2, Swerve swerve){
>>>>>>> parent of 4f751c5... Update
		this.swerve = swerve;
		this.joy = joy1;
				drive = new RobotDrive(ConstantFactory.L_TREAD, ConstantFactory.R_TREAD);
		//		speedX = 0;
		//		speedY = 0;
//		left = new Spark(ConstantFactory.L_TREAD);
//		right = new Spark(ConstantFactory.R_TREAD);
	}

	public void move(){

				if(joy.getRawButton(ConstantFactory.LEFT_TRIGGER) && !swerve.drivingField){
					if(Math.abs(joy.getRawAxis(1)) > .05) speedX = -joy.getRawAxis(1);
					else speedX = 0;
					if(Math.abs(joy.getRawAxis(2)) > .05) speedY = -joy.getRawAxis(2);
					else speedY = 0;
					drive.arcadeDrive(Math.max(-1,Math.min(speedY,1)), Math.max(-1,Math.min(speedX,1)), false);
				}
				else{
					drive.arcadeDrive(0,0,false);
				}
//		double speedleft;
//		double speedright;
//		if(joy.getRawButton(ConstantFactory.LEFT_TRIGGER) && !swerve.drivingField){
//			if (swerve.getTrans().y > 0.0) {
//				if (swerve.getTrans().x > 0.0) {
//					speedleft = -(swerve.getTrans().y - swerve.getTrans().x);
//					speedright = -(Math.max(swerve.getTrans().y,	swerve.getTrans().x));
//				} else {
//					speedleft = -(Math.max(swerve.getTrans().y, -swerve.getTrans().x));
//					speedright = -(swerve.getTrans().y + swerve.getTrans().x);
//				}
//			} else {
//				if (swerve.getTrans().x > 0.0) {
//					speedleft = (Math.max(-swerve.getTrans().y, swerve.getTrans().x));
//					speedright = -(swerve.getTrans().y + swerve.getTrans().x);
//				} else {
//					speedleft = -(swerve.getTrans().y - swerve.getTrans().x);
//					speedright = (Math.max(-swerve.getTrans().y, -swerve.getTrans().x));
//				}
//			}
//			left.set(speedleft);
//			right.set(speedright);
//		}else if(joy.getRawButton(ConstantFactory.LEFT_TRIGGER) && swerve.drivingField){
//			if (swerve.getTrans().y > 0.0) {
//				if (swerve.getTrans().x > 0.0) {
//					speedleft = -(swerve.getTrans().y - swerve.getTrans().x);
//					speedright = -(Math.max(swerve.getTrans().y,	swerve.getTrans().x));
//				} else {
//					speedleft = -(Math.max(swerve.getTrans().y, -swerve.getTrans().x));
//					speedright = -(swerve.getTrans().y + swerve.getTrans().x);
//				}
//			} else {
//				if (swerve.getTrans().x > 0.0) {
//					speedleft = (Math.max(-swerve.getTrans().y, swerve.getTrans().x));
//					speedright = -(swerve.getTrans().y + swerve.getTrans().x);
//				} else {
//					speedleft = -(swerve.getTrans().y - swerve.getTrans().x);
//					speedright = (Math.max(-swerve.getTrans().y, -swerve.getTrans().x));
//				}
//			}
//			left.set(speedleft);
//			right.set(speedright);
//		}
//		else {
//			left.set(0);
//			right.set(0);
//		}
	}
	public void autonomous(double speed){
		drive.arcadeDrive(0,speed);
	}
}
