
package org.usfirst.frc.team1533.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1533.robot.subsystems.*;

public class Robot extends IterativeRobot {
	//TODO Rumble on Joystick
	//TODO Test Labview
	//TODO Field Orientation
	//TODO Vision
	//TODO Easier encoder setting
	//TODO Defense skipping
	//TODO Spring cleaning on code
	//TODO Find angle for actuator
	Swerve swerve;
	Actuator actuator;
	Tank tank;
	Stinger stinger;
	Joystick joy1, joy2, joy3;
	Gyro gyro;

    final String defaultAuto = "Default";
    final String lowbar = "lowBar";
    final String rockwall = "rockwall";
    final String ramparts = "ramparts";
    final String moat = "moat";  

    String autoSelected;
    SendableChooser chooser;
    
    double bottomVoltage, startTime, runTime;
    boolean part1, part2, part3;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	joy1 = new Joystick(0);
    	joy2 = new Joystick(1);
    	swerve = new Swerve(joy1);
    	actuator = new Actuator(joy1, joy2);
    	tank = new Tank(joy1, joy2);
    	stinger = new Stinger(joy2);
    	gyro = new Gyro();
    	
    	
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("Low Bar", lowbar);
        chooser.addObject("Rock Wall", rockwall);
        chooser.addObject("Ramparts", ramparts);
        chooser.addObject("Moat", ramparts);
        SmartDashboard.putData("Autonomous:", chooser);
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
//		Gyro.gyro.reset();
		part1 = true;
		part2 = true;
		part3 = false;
		startTime = System.currentTimeMillis();
		runTime = 10000;
    	autoSelected = (String) chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case lowbar: ConstantFactory.Steering.bottomVoltage = .2;
    		break;
    	case rockwall: ConstantFactory.Steering.bottomVoltage = 1.5;
    		break;
    	case ramparts:  ConstantFactory.Steering.bottomVoltage = 1.2;
    		break;
    	case defaultAuto:
    	default:
    	//no default auto
            break;
    	}
        Scheduler.getInstance().run();
    	if(part1){
			actuator.moveTo(bottomVoltage);
			if(actuator.getAverageVoltage() < bottomVoltage + .1 && actuator.getAverageVoltage() > bottomVoltage - .1){
				part1 = false;
				actuator.set(0);
			}
		}if(part2){
			double z = gyro.getAngle() * -.025;
			swerve.autonomous(0, -.5, z);
			tank.autonomous(1, 0);
			if(System.currentTimeMillis() >= startTime + runTime){
			part2 = false;
			part1 = false;
			part3 = true;
			}
		}if(part3){
			
			part3 = false;
		}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        actuator.move();
        tank.move();
        swerve.move((gyro.getAngle() * -.025));
        stinger.climb();
        stinger.shoot();
        
    	SmartDashboard.putNumber("FL", swerve.modules[0].getAngle()*180/Math.PI);
    	SmartDashboard.putNumber("FR", swerve.modules[1].getAngle()*180/Math.PI);
    	SmartDashboard.putNumber("BL", swerve.modules[2].getAngle()*180/Math.PI);
    	SmartDashboard.putNumber("BR", swerve.modules[3].getAngle()*180/Math.PI); 
    }
    public void disabledPeriodic(){
    	SmartDashboard.putNumber("FL", swerve.modules[0].getAngle()*180/Math.PI);
    	SmartDashboard.putNumber("FR", swerve.modules[1].getAngle()*180/Math.PI);
    	SmartDashboard.putNumber("BL", swerve.modules[2].getAngle()*180/Math.PI);
    	SmartDashboard.putNumber("BR", swerve.modules[3].getAngle()*180/Math.PI);    	
    }

    public void testPeriodic() {
    
    }
    
}
