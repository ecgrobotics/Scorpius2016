package org.usfirst.frc.team1533.scorpius;

import java.util.Timer;

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
	static int buttonPressed = 2;
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
		if(buttonPressed == 0){
			roller.set(1);
		}else if(buttonPressed == 1){
			roller.set(-.4);
		}else if(buttonPressed == 2){
			roller.set(0);
		}
	}
	public static void Update () {
		//Hold down to shoot
		if (Sensory.GetButtonDown(ButtonMapping.RIGHT_BUMPER, 1)) {
			buttonPressed = 0;
			runStingerMotor();
			if (shootStartTime < 0) {
				shootStartTime = System.currentTimeMillis();
			}else if(System.currentTimeMillis()-shootStartTime > ConstantFactory.Stinger.SHOOTER_DELAY *100 && System.currentTimeMillis()-shootStartTime < ConstantFactory.Stinger.SHOOTER_DELAY * 500) {
				buttonPressed = 1;
				runRollerMotor();
			}
			else if (System.currentTimeMillis()-shootStartTime > ConstantFactory.Stinger.SHOOTER_DELAY * 500) {
				buttonPressed = 0;
				runRollerMotor();
			}

		}
		//Hold down to grab ball
		else if (Sensory.GetButtonDown(ButtonMapping.RIGHT_TRIGGER, 1)) {
			buttonPressed = 1;
			runStingerMotor();
			runRollerMotor();
			shootStartTime = -1;
		}
		//Slow all motors
		else {
			buttonPressed = 2;
			runStingerMotor();
			runRollerMotor();
			shootStartTime = -1;
		}

	}
}
