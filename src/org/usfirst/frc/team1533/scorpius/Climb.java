package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.CANTalon;

public class Climb {
	public static CANTalon climbL;
	public static CANTalon climbR;
	static double target;
	
	public static void Initialize () {
		climbL = new CANTalon(ConstantFactory.RobotMap.CLIMB_L);
		climbR = new CANTalon(ConstantFactory.RobotMap.CLIMB_R);
		
	}
	public static void Update() {
<<<<<<< HEAD
		target = Sensory.GetButtonDown(ButtonMapping.A, 1) ? 1 : Sensory.GetButtonDown(ButtonMapping.Y, 1) ? -1 : 0;
=======
		target = Sensory.GetButtonDown(ButtonMapping.Y, 1) ? 1 : Sensory.GetButtonDown(ButtonMapping.A, 1) ? -1 : 0;
>>>>>>> d127598d3fbb5fbe3e0a34f63b8fc068df6d4ed8
		climbL.set(target);
		climbR.set(-target);
	}
}
