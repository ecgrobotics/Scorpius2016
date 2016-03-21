package org.usfirst.frc.team1533.scorpius;

//This class is an abstraction between VM 
//callbacks and Scorpius. It manages what gets called
//when.
public class Splicer {

	public static void Initialize (boolean autonomous) {
		Sensory.Initialize();
////		Lucid.Initialize();
		if (autonomous) Autonomous.Initialize();
		else{
			Actuator.Initialize();
			Gyro.Initialize();
			Climb.Initialize();
			Panzer.Initialize();
			Swerve.Initialize();
			Stinger.Initialize();
		}
	}

	//Implicitly dynamic timestep
	public static void Update (boolean autonomous) {
//		Gyro.Update();
		Sensory.Update();
		Gyro.Update();
		if (autonomous) Autonomous.Update();
////		//Call all updates
		else{
			Actuator.Update();
			Climb.Update();
////			if(SwerveBroken.encoderBroken){
////				SwerveBroken.Update();
////			}else{
			
				Panzer.Update();
//				Swerve.enable();
//				Swerve.Update();
			}
			Stinger.Update();
//		}

	}
}
