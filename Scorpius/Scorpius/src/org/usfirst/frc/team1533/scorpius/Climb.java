package org.usfirst.frc.team1533.scorpius;

import java.util.Timer;

import edu.wpi.first.wpilibj.CANTalon;

public class Climb {
	public static CANTalon climbL;
	public static CANTalon climbR;
	public static Timer timer;
	
	public static void Initialize(){
		climbL = new CANTalon(ConstantFactory.RobotMap.CLIMB_L);
		climbR = new CANTalon(ConstantFactory.RobotMap.CLIMB_R);
		
	}
	public static void Update(){
		if(Sensory.pad1.getRawButton(2)){
		climbL.set(-.8);
		climbR.set(.8);
		}
		else if(Sensory.pad1.getRawButton(4)){
		climbL.set(.8);
		climbR.set(-.8);
		}
		else{
		climbL.set(0);
		climbR.set(0);
		}
//	public void ClimbPullup(){
//		
//	}
//	
	}

}
