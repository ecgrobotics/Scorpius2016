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
		Climb.Initialize();
		Panzer.Initialize();
		Swerve.Initialize();
		Stinger.Initialize();
	}
	
	//Implicitly dynamic timestep
	public static void Update () {
		//DEBUG //Update Gyro
		Gyro.Update();
		//Update Sensory
		Sensory.Update();
		//Call all updates
		Actuator.Update();
		Climb.Update();
		Panzer.Update();
		Swerve.Update();
		Stinger.Update();
	}
}
