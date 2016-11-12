package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class Tank {
	Joystick joy;
	double speedX, speedY;
	Swerve swerve;
	SpeedController left, right;
	Gyro gyro;

	public Tank(Joystick joy1, Swerve swerve, Gyro gyro){
		this.swerve = swerve;
		this.joy = joy1;
		left = new Spark(ConstantFactory.L_TREAD);
		right = new Spark(ConstantFactory.R_TREAD);
		this.gyro = gyro;
	}
	public void move(){
		double transx, transy;
		double angle = gyro.getAngle() * Math.PI / 180;
		if(joy.getRawButton(ConstantFactory.LEFT_TRIGGER)){
			if(swerve.drivingField){
				transx = Math.min(Math.max(-1,2*(-joy.getX()*Math.cos(angle) - joy.getY()*Math.sin(angle))), 1);
				transy = Math.min(Math.max(-1,2*(-joy.getX()*Math.sin(angle) + joy.getY()*Math.cos(angle))), 1);
			}else{
				transx = Math.min(Math.max(-1, 2*joy.getX()), 1);
				transy = Math.min(Math.max(-1,2*joy.getY()), 1);
			}
			left.set((transy-transx));
			right.set((transy + transx));

		}
		else{
			left.set(0);
			right.set(0);
		}
	}

	public void autonomous(double speed){
		right.set(-speed);
		left.set(-speed);
	}
}
