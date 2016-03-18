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
		target = Sensory.GetButtonDown(ButtonMapping.A, 1) ? 1 : Sensory.GetButtonDown(ButtonMapping.Y, 1) ? -1 : Sensory.GetButtonDown(ButtonMapping.X, 1) ? .25 : 0;
		climbL.set(target);
		climbR.set(-target);
	}
}
