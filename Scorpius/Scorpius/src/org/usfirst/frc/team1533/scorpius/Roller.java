package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.Spark;

public class Roller {
	public static Spark roller;
	public static void Initialize(){
		//pwm2
		//spark
		roller = new Spark(ConstantFactory.RobotMap.ROLLER);
	}
	public static void Update(){
	if(Sensory.pad1.getRawButton(4)){
		roller.set(1);
	}
	else if(Sensory.pad1.getRawButton(2)){
		roller.set(-1);
	}
	else{
		roller.set(0);
	}
		
	}
}
