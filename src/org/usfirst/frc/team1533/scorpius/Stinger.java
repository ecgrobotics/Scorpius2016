package org.usfirst.frc.team1533.scorpius;

import java.util.Timer;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class Stinger {

	//State vars
	static SpeedController stingerL;
	static SpeedController stingerR;
	static double angle;
	//Timing vars
	static Timer timer;
	static int buttonPressed;
	static long shootStartTime = -1;

	public static void Initialize () {
		//Initialize Sparks
		stingerL = new Spark(ConstantFactory.RobotMap.STINGER_L);
		stingerR = new Spark(ConstantFactory.RobotMap.STINGER_R);
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
	public static void Update () {
		//Hold down to shoot
		if (Sensory.GetButtonDown(ButtonMapping.RIGHT_BUMPER, 1)) {
			runStingerMotor();
			if (shootStartTime < 0) {
				shootStartTime = System.currentTimeMillis();
			} else if (System.currentTimeMillis()-shootStartTime > ConstantFactory.Stinger.SHOOTER_DELAY * 1000) {
			}
		}
		//Hold down to grab ball
		else if (Sensory.GetButtonDown(ButtonMapping.RIGHT_TRIGGER, 1)) {
			runStingerMotor();
			shootStartTime = -1;
		}
		//Slow all motors
		else {
			runStingerMotor();
			shootStartTime = -1;
		}

	}
}
