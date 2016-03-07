package org.usfirst.frc.team1533.scorpius;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class Stinger {
	
	//State vars
	static SpeedController stingerL;
	static SpeedController stingerR;
	static double angle;
	//Timing vars
	static Timer timer;
	
	public static void Initialize () {
		//Initialize Sparks
		stingerL = new Spark(ConstantFactory.RobotMap.STINGER_L);
		stingerR = new Spark(ConstantFactory.RobotMap.STINGER_R);
		//Initialize Timer
		timer = new Timer();
	}
	
	public static void Update () { //REMOVE //Might not be necessary
		if(Sensory.pad1.getRawButton(1)){
			stingerL.set(-.4);
			stingerR.set(.4);
			
		}
		else if(Sensory.pad1.getRawButton(3)){
			stingerL.set(1);
			stingerR.set(-1);
		}
		else{
			stingerL.set(0);
			stingerR.set(0);

		}
	}
	
	public static boolean Shoot (double elevation) { //UPDATE //Start running motor at specified distance from target
		return Actuator.SetAngle(elevation, new Callback() {
			@Override
			public void Execute () {
				Start();
				timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
					  Stop();
				  }
				}, Math.round(ConstantFactory.Stinger.SHOOTER_TIMEOUT*1000));
			}
		});
	}
	
	private static void Start () {
		//Full power to the motors
		stingerL.set(1.0); //UPDATE //Direction
		stingerR.set(1.0); //UPDATE //Direction
	}
	
	private static void Stop () {
		//Stop the motors
		stingerL.set(0);
		stingerR.set(0);
	}
}
