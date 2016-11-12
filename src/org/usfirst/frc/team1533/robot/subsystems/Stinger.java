package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;
import org.usfirst.frc.team1533.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Stinger {
	SpeedController climbR;
	SpeedController climbL;
	static SpeedController shooterR;
	static SpeedController shooterL;
	static SpeedController roller;
	Timer timer;
	Joystick joy;
	double shootStartTime;
	String pewpew;
	Relay flashlight;
	boolean on, flashy;

	public Stinger(Joystick joy2){
		on = false;
		flashy = false;
		climbR = new CANTalon(ConstantFactory.CLIMB_R);
		climbL = new CANTalon(ConstantFactory.CLIMB_L);
		shooterR = new Spark(ConstantFactory.STINGER_R);
		shooterL = new Spark(ConstantFactory.STINGER_L);
		roller = new Spark(ConstantFactory.ROLLER);
		timer = new Timer();
		this.joy = joy2;
		shootStartTime = -1;
		pewpew = new String("no");
		flashlight = new Relay(ConstantFactory.FLASHLIGHT, Relay.Direction.kForward);
	}

	public void climb(){
		double target = joy.getRawButton(ConstantFactory.A2) ? 1 :joy.getRawButton(ConstantFactory.Y2)? -1 : joy.getRawButton(ConstantFactory.X2) ? .25 : 0;
		climbL.set(-target);
		climbR.set(target);
	}
	public void flashlight(){
		if(joy.getRawButton(ConstantFactory.LEFT_BUMPER2)){
			on = true;
		}else {
			if(on) flashy = !flashy;
			on = false;
		}
		if(flashy) flashlight.set(Relay.Value.kOn);
		else	flashlight.set(Relay.Value.kOff);
		SmartDashboard.putBoolean("Flashlight?", flashy);
	}

	public void shoot(){
		SmartDashboard.putString("shooting", pewpew);
		if (joy.getRawButton(ConstantFactory.RIGHT_BUMPER2) || joy.getRawButton(ConstantFactory.LEFT_BUMPER2)){
			if(joy.getRawButton(ConstantFactory.RIGHT_BUMPER2))
				runShooter(0, 1);
			if(joy.getRawButton(ConstantFactory.LEFT_BUMPER2))
				runShooter(0, .5);
			if (shootStartTime < 0) shootStartTime = System.currentTimeMillis();
			else if(System.currentTimeMillis()-shootStartTime > 50 && System.currentTimeMillis()-shootStartTime < 1250) runRoller(1);
			else if (System.currentTimeMillis()-shootStartTime > 1250) runRoller(0);
		}else if(joy.getRawButton(ConstantFactory.RIGHT_TRIGGER2) || Robot.joy1.getPOV()==90 || Robot.joy1.getPOV()==0){
			runShooter(1, 1);
			runRoller(1);
			shootStartTime = -1;
		}

		else if(joy.getPOV() == 270){
			runShooter(0,1);
			if(joy.getRawButton(ConstantFactory.LEFT_TRIGGER2))
				runRoller(0);
			shootStartTime = -1;
		}
		else{
			runShooter(2, 1);
			runRoller(2);
			shootStartTime = -1;
		}
	}

	public static void runShooter(int buttonPressed, double percent){
		if(buttonPressed == 0){			//shoots ball
			shooterL.set(-1*percent);
			shooterR.set(1*percent);
		}else if(buttonPressed == 1){	//grabs ball
			shooterL.set(.6);
			shooterR.set(-.6);
		}else{
			shooterL.set(0);
			shooterR.set(0);
		}
	}

	public static void runRoller(int buttonPressed){
		if(buttonPressed == 0)	roller.set(1);			//out
		else if(buttonPressed == 1)	roller.set(-.4);	//in
		else roller.set(0);
	}
	static public void auto(double start){
		if(System.currentTimeMillis() < (start+100))
			runShooter(0,1);
		else if((System.currentTimeMillis() >= (start+ 1000)) && (System.currentTimeMillis() <= (start+1750))){
			runShooter(0,1);
			runRoller(0);
		}
	}




}