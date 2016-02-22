package org.usfirst.frc.team1533.scorpius;

//This class is an abstraction between VM 
//callbacks and Scorpius. It manages what gets called
//when.
public class Splicer {
	
	public static void Start () {
		//Initialize Gyro
		Gyro.Initialize();
		//Initialize Sensory
		Sensory.Initialize();
		//Call all initializers
		Actuator.Initialize();
//		Lucid.Initialize();
		Panzer.Initialize();
		Swerve.Initialize();
		Roller.Initialize();
		Stinger.Initialize();
		Climb.Initialize();
	}
	
	//Implicitly dynamic timestep
	public static void Update () {
		//Update Sensory
		Sensory.Update();
		//Call all updates
		Actuator.Update();
		//Lucid.Update();
		Panzer.Update();
		Roller.Update();
		Swerve.Update();
		Stinger.Update();
		Climb.Update();
	}
}
