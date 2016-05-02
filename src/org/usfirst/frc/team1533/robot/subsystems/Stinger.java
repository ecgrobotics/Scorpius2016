package org.usfirst.frc.team1533.robot.subsystems;

import org.usfirst.frc.team1533.robot.ConstantFactory;

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
	SpeedController shooterR;
	SpeedController shooterL;
	SpeedController roller;
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
		double target = joy.getRawButton(ConstantFactory.A2) ? 1 :joy.getRawButton(ConstantFactory.Y2)? -1 : joy.getRawButton(ConstantFactory.X2) ? .25 : joy.getRawButton(ConstantFactory.B2) ? -.25 : 0;
		climbL.set(target);
		climbR.set(-target);
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
		if (joy.getRawButton(ConstantFactory.RIGHT_BUMPER2)){
			runShooter(0);
			if (shootStartTime < 0) shootStartTime = System.currentTimeMillis();
			else if(System.currentTimeMillis()-shootStartTime > 50 && System.currentTimeMillis()-shootStartTime < 250) runRoller(1);
			else if (System.currentTimeMillis()-shootStartTime > 250) runRoller(0);
		}else if(joy.getRawButton(ConstantFactory.RIGHT_TRIGGER2)){
			runShooter(1);
			runRoller(1);
			shootStartTime = -1;
		}
		else{
			runShooter(2);
			runRoller(2);
			shootStartTime = -1;
		}
	}

	public void runShooter(int buttonPressed){
		if(buttonPressed == 0){			//shoots ball
			shooterL.set(-1);
			shooterR.set(1);
			pewpew = "PEW! PEW!";
		}else if(buttonPressed == 1){	//grabs ball
			shooterL.set(.4);
			shooterR.set(-.4);
			pewpew = "grab";
		}else{
			shooterL.set(0);
			shooterR.set(0);
			pewpew = "no";
		}
	}

	public void runRoller(int buttonPressed){
		if(buttonPressed == 0)	roller.set(1);			//out
		else if(buttonPressed == 1)	roller.set(-.4);	//in
		else roller.set(0);
	}




}
