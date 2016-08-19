
package org.usfirst.frc.team1533.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1533.robot.subsystems.*;

public class Robot extends IterativeRobot {
	Swerve swerve;
	Actuator actuator;
	Tank tank;
	Stinger stinger;
	public static Joystick joy1, joy2;
	Gyro gyro;
	Vision vision;
	public static AnalogInput ballSenseLeft = new AnalogInput(5);
	public static AnalogInput ballSenseRight = new AnalogInput(6);


	final String lowbar = "lowBar";
	final String rockwall = "rockwall";
	final String ramparts = "ramparts";
	final String moat = "moat";  

	String autoSelected;
	SendableChooser chooser;
	String spaceSelected;
	SendableChooser chooser2;

	double  startTime, runTime, alignTime;
	boolean part1, part2, part3, defense, over, goingover,  stop, part4, part5;


	public void robotInit() {
		joy1 = new Joystick(0);
		joy2 = new Joystick(1);
		gyro = new Gyro();
		vision = new Vision();
		swerve = new Swerve(joy1, joy2, gyro, vision);
		tank = new Tank(joy1, swerve, gyro);
		stinger = new Stinger(joy2);
		actuator = new Actuator(joy2, vision);

		gyro.gyro.calibrate();
		chooser = new SendableChooser();
		chooser.addObject("Rock Wall", rockwall);
		chooser.addObject("Low Bar", lowbar);
		chooser.addObject("Ramparts", ramparts);
		chooser.addObject("Moat", ramparts); 
		SmartDashboard.putData("Autonomous:", chooser);
		chooser2 = new SendableChooser();
		chooser2.addObject("slot 1", "1");
		chooser2.addObject("slot 2", "2");
		chooser2.addObject("slot 3", "3");
		chooser2.addObject("slot 4", "4");
		chooser2.addObject("slot 5", "5");
		SmartDashboard.putData("Space:", chooser2);


	}


	public void autonomousInit() {
		gyro.reset();
		part1 = true;
		part2 = true;
		part3 = false;
		part4 = false;
		part5 = false;
		startTime = System.currentTimeMillis();
		runTime = 6000;
		alignTime = 1500;
		autoSelected = (String) chooser.getSelected();
		spaceSelected = (String) chooser2.getSelected();

		System.out.println("Auto selected: " + autoSelected);
	}


	public void autonomousPeriodic() {
		switch(autoSelected) {
		case rockwall: ConstantFactory.Steering.bottomVoltage = 1.44;
		runTime =4750;
		break;
		case ramparts:  ConstantFactory.Steering.bottomVoltage = 1.44;
		runTime = 4000;
		break;
		case lowbar: 
			ConstantFactory.Steering.bottomVoltage = .31;
			runTime = 4550;
			break;
		}
		Scheduler.getInstance().run();
		if(part1){
			if(actuator.getAverageVoltage() > ConstantFactory.Steering.bottomVoltage || actuator.getAverageVoltage() < ConstantFactory.Steering.bottomVoltage) actuator.autonomous(ConstantFactory.Steering.bottomVoltage);
			else if(actuator.getAverageVoltage() < ConstantFactory.Steering.bottomVoltage + .05 && actuator.getAverageVoltage() > ConstantFactory.Steering.bottomVoltage - .05){
				part1 = false;
			}
		}if(part2){

			swerve.autonomous(0, -.6, gyro.angleCorrect());
			tank.autonomous(-1);
			if((System.currentTimeMillis() >= startTime + runTime)){
				swerve.autonomous(0, 0, 0);
				tank.autonomous(0);
				part2 = false;
				part1 = false;
				part3 = true;
				Swerve.rotating = true;
			}
		}if(part3){
			if(Swerve.rotating)
				swerve.pivot(180);
			startTime = System.currentTimeMillis();
		}
		else if(!Swerve.rotating){
			if(System.currentTimeMillis() >= startTime + alignTime)
				swerve.visionAlign();
			else{
				part3 = false;
				part4 = true;
				Swerve.rotating = true;
			}
		}
		if(part4){
			if(Swerve.rotating){
				swerve.pivot(180);
				startTime = System.currentTimeMillis();
			}
			else{
				if(gyro.acc.getX() <= 2.6 || System.currentTimeMillis() <= startTime +500){
					swerve.autonomous(0, -.6, gyro.angleCorrect());
					tank.autonomous(1);
				}
				else if(actuator.getAverageVoltage() <= 3.7 ){
					actuator.autonomous(4);
					tank.autonomous(.5);
					swerve.autonomous(0, -.3, gyro.angleCorrect());
				}
				else{
					part4 = false;
					part5 = true;
					startTime = System.currentTimeMillis();
				}
			}
		}if(part5){
			if(System.currentTimeMillis() <= startTime + 2000)
				stinger.autoExtend();
			else if(System.currentTimeMillis() <= startTime + 6000)
				stinger.autoRetract();
		}

	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		actuator.move();
		tank.move();
		swerve.move();
		stinger.climb();
		stinger.shoot();
		vision.process();
		
		SmartDashboard.putNumber("FL Encoder", swerve.modules[0].getAngle()*180/Math.PI);
		SmartDashboard.putNumber("FR Encoder", swerve.modules[1].getAngle()*180/Math.PI);
		SmartDashboard.putNumber("BL Encoder", swerve.modules[2].getAngle()*180/Math.PI);
		SmartDashboard.putNumber("BR Encoder", swerve.modules[3].getAngle()*180/Math.PI);

		SmartDashboard.putNumber("Left Ball Sensor", ballSenseLeft.getAverageVoltage());
		SmartDashboard.putNumber("Right Ball Sensor", ballSenseRight.getAverageVoltage());
	}
	public void disabledPeriodic(){
		SmartDashboard.putNumber("FL Encoder", swerve.modules[0].getAngle()*180/Math.PI);
		SmartDashboard.putNumber("FR Encoder", swerve.modules[1].getAngle()*180/Math.PI);
		SmartDashboard.putNumber("BL Encoder", swerve.modules[2].getAngle()*180/Math.PI);
		SmartDashboard.putNumber("BR Encoder", swerve.modules[3].getAngle()*180/Math.PI);    
	}

	public void testPeriodic() {

	}
}
