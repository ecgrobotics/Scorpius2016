package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;

public class Stinger {
	SpeedController climbR;
	SpeedController climbL;
	SpeedController shooterR;
	SpeedController shooterL;
	SpeedController roller;
	Timer timer;
	Joystick joy1, joy2;
	
	public Stinger(Joystick joy1, Joystick joy2){
		climbR = new CANTalon(ConstantFactory.CLIMB_R);
		climbL = new CANTalon(ConstantFactory.CLIMB_L);
		shooterR = new Spark(ConstantFactory.STINGER_R);
		shooterL = new Spark(ConstantFactory.STINGER_L);
		roller = new Spark(ConstantFactory.ROLLER);
		timer = new Timer();
		this.joy1 = joy1;
		this.joy2 = joy2;
	}
	
	public void climb(){
		double target = joy2.getRawButton(ConstantFactory.A) ? 1 :joy2.getRawButton(ConstantFactory.Y)? -1 : joy2.getRawButton(ConstantFactory.X) ? .25 : joy2.getRawButton(ConstantFactory.B) ? -.25 : 0;
		climbL.set(target);
		climbR.set(-target);
	}
	
	public void shoot(){
		
	}
	
	public void runShooter(){
		
	}
	
	public void runRoller(){
		
	}
	
	
	
	
}
