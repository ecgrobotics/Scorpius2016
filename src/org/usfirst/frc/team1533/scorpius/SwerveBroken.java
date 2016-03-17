package org.usfirst.frc.team1533.scorpius;

public class SwerveBroken {	
	static boolean encoderBroken = false, part1 = true, part2 = false;
	static int badModule;

	public SwerveBroken(){ 
		encoderBroken =! encoderBroken;
	}
	public static void Update(){
		if(part1){
			PositionTank();
			part1 = false;
			part2 = true;
		}if(part2){
			RotateWheel();
			Drive();
		}
	}
	public static void PositionTank(){
		for(int i = 0; i < 4; i++){
			Swerve.modules[i].set(0, 0);
		}
	}
	public static void RotateWheel(){
		for(int i = 0; i< Swerve.modules.length; i++){
			if(Swerve.modules[i].steerEncoder.getAngle() != 0){
				badModule = i;
			}
		}
		if(Sensory.GetButtonDown(ButtonMapping.RIGHT_BUMPER, 0)){
			Swerve.modules[badModule].manualRotation(.2);
		}else if(Sensory.GetButtonDown(ButtonMapping.RIGHT_TRIGGER, 0)){
			Swerve.modules[badModule].manualRotation(-.2);
		}else Swerve.modules[badModule].manualRotation(0);
	}
	public static void Drive(){
		for(int i = 0; i < 4; i+=2){
			Swerve.modules[i].set(Sensory.GetAxis(2, 1));
		}
		for(int i = 1; i < 4; i+=2){
			Swerve.modules[i].set(Sensory.GetAxis(1, 1));
		}
		Panzer.Update();
	}


}
