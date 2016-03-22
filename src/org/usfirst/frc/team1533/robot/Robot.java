
package org.usfirst.frc.team1533.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1533.robot.subsystems.*;

public class Robot extends IterativeRobot {
	Swerve swerve;
	Actuator actuator;
	Tank tank;

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
    	swerve = new Swerve();
    	actuator = new Actuator();
    	tank = new Tank();
    	
    	
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
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
