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
	Joystick joy;
	double shootStartTime;

	public Stinger(Joystick joy2){
		climbR = new CANTalon(ConstantFactory.CLIMB_R);
		climbL = new CANTalon(ConstantFactory.CLIMB_L);
		shooterR = new Spark(ConstantFactory.STINGER_R);
		shooterL = new Spark(ConstantFactory.STINGER_L);
		roller = new Spark(ConstantFactory.ROLLER);
		timer = new Timer();
		this.joy = joy2;
		shootStartTime = -1;
	}

	public void climb(){
		double target = joy.getRawButton(ConstantFactory.A) ? 1 :joy.getRawButton(ConstantFactory.Y)? -1 : joy.getRawButton(ConstantFactory.X) ? .25 : joy.getRawButton(ConstantFactory.B) ? -.25 : 0;
		climbL.set(target);
		climbR.set(-target);
	}

	public void shoot(){
		if (joy.getRawButton(ConstantFactory.RIGHT_BUMPER)){
			runShooter(0);
			if (shootStartTime < 0) shootStartTime = System.currentTimeMillis();
			else if(System.currentTimeMillis()-shootStartTime > 50 && System.currentTimeMillis()-shootStartTime < 250) runRoller(1);
			else if (System.currentTimeMillis()-shootStartTime > 250) runRoller(0);
		}else if(joy.getRawButton(ConstantFactory.RIGHT_TRIGGER)){
			runShooter(1);
			runRoller(1);
			shootStartTime = -1;
		}else{
			runShooter(2);
			runRoller(2);
			shootStartTime = -1;
		}
	}

	public void runShooter(int buttonPressed){
		if(buttonPressed == 0){			//shoots ball
			shooterL.set(-1);
			shooterR.set(1);
		}else if(buttonPressed == 1){	//grabs ball
			shooterL.set(.4);
			shooterR.set(-.4);
		}else{
			shooterL.set(0);
			shooterR.set(0);
		}
	}

	public void runRoller(int buttonPressed){
		if(buttonPressed == 0)	roller.set(1);			//out
		else if(buttonPressed == 1)	roller.set(-.4);	//in
		else roller.set(0);
	}




}
