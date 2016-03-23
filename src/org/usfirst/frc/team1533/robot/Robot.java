
package org.usfirst.frc.team1533.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1533.robot.subsystems.*;

public class Robot extends IterativeRobot {
	Swerve swerve;
	Actuator actuator;
	Tank tank;
	Stinger stinger;
	Joystick joy1, joy2;

    final String defaultAuto = "Default";
    final String lowbar = "lowBar";
    final String rockwall = "rockwall";
    final String ramparts = "ramparts";
    final String moat = "moat";  

    String autoSelected;
    SendableChooser chooser;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	joy1 = new Joystick(0);
    	joy2 = new Joystick(1);
    	swerve = new Swerve();
    	actuator = new Actuator(joy2);
    	tank = new Tank(joy1);
    	stinger = new Stinger(joy1, joy2);
    	
    	
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("Low Bar", lowbar);
        chooser.addObject("Rock Wall", rockwall);
        chooser.addObject("Ramparts", ramparts);
        chooser.addObject("Moat", moat);
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
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case lowbar:
    		
    		break;
    	case rockwall:
    		break;
    	case ramparts:
    		break;
    	case moat:
    		break;
    	case defaultAuto:
    	default:
    	//no default auto
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        actuator.move();
        tank.move();
        swerve.driveNormal(joy1.getX()/2, -joy1.getY()/2, joy1.getZ()/2);
        stinger.climb();
    }
    

    public void testPeriodic() {
    
    }
    
}
