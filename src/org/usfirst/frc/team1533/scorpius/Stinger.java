package org.usfirst.frc.team1533.scorpius;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class Stinger {
	
	//State vars
	static SpeedController stingerL;
	static SpeedController stingerR;
	static SpeedController roller;
	static double angle;
	//Timing vars
	static Timer timer;
	static int buttonPressed;
	static long shootStartTime = -1;
	
	public static void Initialize () {
		//Initialize Sparks
		stingerL = new Spark(ConstantFactory.RobotMap.STINGER_L);
		stingerR = new Spark(ConstantFactory.RobotMap.STINGER_R);
		roller = new Spark(ConstantFactory.RobotMap.ROLLER);
		//Initialize Timer
		timer = new Timer();
	}
	public static void runStingerMotor(){
		if(buttonPressed == 0){
			//shoots ball
			stingerL.set(-ConstantFactory.Stinger.STINGER_POWER_SHOOT_PERCENT);
			stingerR.set(ConstantFactory.Stinger.STINGER_POWER_SHOOT_PERCENT);
		}
		else if(buttonPressed == 1){
			//grabs ball
			stingerL.set(ConstantFactory.Stinger.STINGER_POWER_GRASP_PERCENT);
			stingerR.set(-ConstantFactory.Stinger.STINGER_POWER_GRASP_PERCENT);

		}
		else if(buttonPressed == 2){
			stingerL.set(0);
			stingerR.set(0);
		}
	}
	public static void runRollerMotor(){
		if (buttonPressed == 0) {
			//shoots ball
			roller.set(ConstantFactory.Stinger.STINGER_POWER_SHOOT_PERCENT);
		}
		else if(buttonPressed == 1) {
			//grabs ball
			roller.set(-ConstantFactory.Stinger.STINGER_POWER_SHOOT_PERCENT);
		}
		else if(buttonPressed == 2){
			roller.set(0);
		}
	}
	public static void Update () {
		//Hold down to shoot
		if (Sensory.GetButtonDown(ButtonMapping.RIGHT_BUMPER, 1)) {
			buttonPressed = 0;
			runStingerMotor();
//			try {
//				timer.wait(Math.round(ConstantFactory.Stinger.SHOOTER_DELAY*1000));
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if (shootStartTime < 0) {
				shootStartTime = System.currentTimeMillis();
			} else if (System.currentTimeMillis()-shootStartTime > ConstantFactory.Stinger.SHOOTER_DELAY * 1000) {
				runRollerMotor();
			}
//			In case it continuously throws above method
//			timer.schedule(new TimerTask() {
//			  @Override
//			  public void run() {
//			  }
//			}, Math.round(ConstantFactory.Stinger.SHOOTER_DELAY*1000));
//			runRollerMotor();
		}
		//Hold down to grab ball
		else if (Sensory.GetButtonDown(ButtonMapping.RIGHT_TRIGGER, 1)) {
			buttonPressed = 1;
			runRollerMotor();
			runStingerMotor();
			shootStartTime = -1;
		}
		//Slow all motors
		else {
			buttonPressed = 2;
			runRollerMotor();
			runStingerMotor();
			shootStartTime = -1;
		}

	}
}
