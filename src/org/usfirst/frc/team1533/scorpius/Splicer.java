package org.usfirst.frc.team1533.scorpius;

//This class is an abstraction between VM 
//callbacks and Scorpius. It manages what gets called
//when.
public class Splicer {
		
	public static void Initialize (boolean autonomous) {
		//Initialize the camera
		Lucid.Initialize();
		//Initialize Gyro
		Gyro.Initialize();
		//Initialize Sensory
		Sensory.Initialize();
		//Initialize Autonomous
		if (autonomous) Autonomous.Initialize();
		//Call all initializers
		Actuator.Initialize();
		Climb.Initialize();
		Panzer.Initialize();
		Swerve.Initialize();
		Stinger.Initialize();
	}
	
	//Implicitly dynamic timestep
	public static void Update (boolean autonomous) {
		//Update the camera
		Lucid.Update();
		//Update Gyro
		Gyro.Update();
		//Update Sensory
		Sensory.Update();
		//Update Autonomous
		if (autonomous) Autonomous.Update();
		//Call all updates
		Actuator.Update();
		Climb.Update();
		Panzer.Update();
		Swerve.Update();
		Stinger.Update();
		
	}
}
