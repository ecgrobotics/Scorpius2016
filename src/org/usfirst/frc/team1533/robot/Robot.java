
package org.usfirst.frc.team1533.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1533.robot.subsystems.*;
import com.ni.vision.NIVision.Image;

public class Robot extends IterativeRobot {
	//TODO Rumble on Joystick
	//TODO Easier encoder setting
	//TODO Defense skipping
	Swerve swerve;
	Actuator actuator;
	Tank tank;
	Stinger stinger;
	public static Joystick joy1, joy2, joy3;
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

	double  startTime, runTime;
	boolean part1, part2, part3;


	public void robotInit() {
		joy1 = new Joystick(0);
		joy2 = new Joystick(1);
		gyro = new Gyro();
		vision = new Vision(joy1, joy2);
		swerve = new Swerve(joy1, joy2, gyro, vision);
		tank = new Tank(joy1, joy2, swerve);
		actuator = new Actuator(joy1, joy2, vision);
		stinger = new Stinger(joy2);

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


		//    	CameraServer cam = CameraServer.getInstance();
		//    	cam.setQuality(20);
		//    	cam.startAutomaticCapture("cam0");
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
	public void autonomousInit() {
		Gyro.gyro.reset();
		part1 = true;
		part2 = true;
		part3 = false;
		startTime = System.currentTimeMillis();
		runTime = 10000;
		autoSelected = (String) chooser.getSelected();
		spaceSelected = (String) chooser2.getSelected();

		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
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
			if(System.currentTimeMillis() >= startTime + runTime){
				swerve.autonomous(0, 0, 0);
				tank.autonomous(0);
				part2 = false;
				part1 = false;
				Swerve.rotating = true;
			}
		}if(Swerve.rotating){
			swerve.pivot(180);
		}
	}

	/**
	 * This function is called periodically during operator control
	 */


	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		actuator.move();
		tank.move();
		swerve.move();
		stinger.climb();
		stinger.shoot();
		stinger.flashlight();
		vision.process();

		//        if(i<100 && i> itemp){
			//        	i++;
		//        	itemp = i;
		//        }else if(i>0 && itemp>i){
		//        	i--;
		//        	itemp = i;
		//        }
		//        SmartDashboard.putNumber("Duncan", i);

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
		
		Gyro.gyro.calibrate();
	}

	public void testPeriodic() {

	}
}
