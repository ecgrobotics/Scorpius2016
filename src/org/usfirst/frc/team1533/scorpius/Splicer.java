package org.usfirst.frc.team1533.scorpius;

//This class is an abstraction between VM 
//callbacks and Scorpius. It manages what gets called
//when.
public class Splicer {
		
	public static void Initialize (boolean autonomous) {
<<<<<<< HEAD
		//Initialize the camera
		Lucid.Initialize();
=======
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
		//Initialize Gyro
		Gyro.Initialize();
		//Initialize Sensory
		Sensory.Initialize();
		//Initialize Egghead
		if (autonomous) Egghead.Initialize();
		//Call all initializers
		Actuator.Initialize();
		Climb.Initialize();
		Panzer.Initialize();
		Swerve.Initialize();
		Stinger.Initialize();
	}
	
	//Implicitly dynamic timestep
	public static void Update (boolean autonomous) {
<<<<<<< HEAD
		//Update the camera
		Lucid.Update();
=======
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
		//Update Gyro
		Gyro.Update();
		//Update Sensory
		Sensory.Update();
		//Update Egghead
		if (autonomous) Egghead.Update();
		//Call all updates
		Actuator.Update();
		Climb.Update();
		Panzer.Update();
		Swerve.Update();
		Stinger.Update();
<<<<<<< HEAD
		
=======
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
	}
}
