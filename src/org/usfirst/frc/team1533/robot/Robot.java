
package org.usfirst.frc.team1533.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Comparator;

import org.usfirst.frc.team1533.robot.subsystems.*;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

public class Robot extends IterativeRobot {
	//TODO Rumble on Joystick
	//TODO Test Labview
	//TODO Easier encoder setting
	//TODO Defense skipping
	//TODO easy rotation
	Swerve swerve;
	Actuator actuator;
	Tank tank;
	Stinger stinger;
	Joystick joy1, joy2, joy3;
	Gyro gyro;
    int session;
    Image frame;


    final String lowbar = "lowBar";
    final String rockwall = "rockwall";
    final String ramparts = "ramparts";
    final String moat = "moat";  

    String autoSelected;
    SendableChooser chooser;
    String spaceSelected;
    SendableChooser chooser2;
    
    double  startTime, runTime;
    boolean part1, part2;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    	joy1 = new Joystick(0);
    	joy2 = new Joystick(1);
    	gyro = new Gyro();
    	tank = new Tank(joy1, joy2);
    	swerve = new Swerve(joy1, gyro);
    	actuator = new Actuator(joy1, joy2);
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
        
        
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera("cam1",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);


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
    	case rockwall: ConstantFactory.Steering.bottomVoltage = 1.3;
		break;
	case ramparts:  ConstantFactory.Steering.bottomVoltage = 1.3;
		break;
    	case lowbar: ConstantFactory.Steering.bottomVoltage = .28;
    		break;
    		}
        Scheduler.getInstance().run();
    	if(part1){
    		SmartDashboard.putNumber("voltage", actuator.getAverageVoltage());
    		if(actuator.getAverageVoltage() > ConstantFactory.Steering.bottomVoltage || actuator.getAverageVoltage() < ConstantFactory.Steering.bottomVoltage) actuator.autonomous(ConstantFactory.Steering.bottomVoltage);
    		else if(actuator.getAverageVoltage() < ConstantFactory.Steering.bottomVoltage + .05 && actuator.getAverageVoltage() > ConstantFactory.Steering.bottomVoltage - .05){
				part1 = false;
				}
		}if(part2){
			double z = gyro.getAngle() * -.02;
			swerve.autonomous(0, -.5, z);
			tank.autonomous(0, -1);
			if(System.currentTimeMillis() >= startTime + runTime){
			part2 = false;
			part1 = false;
			}
		}
    }

    /**
     * This function is called periodically during operator control
     */
    public void operatorControl(){
        NIVision.IMAQdxStartAcquisition(session);
        NIVision.IMAQdxGrab(session, frame, 1);
        CameraServer.getInstance().setImage(frame);
       NIVision.IMAQdxStartAcquisition(session);
    }
    public void teleopPeriodic() {
    	SmartDashboard.putNumber("Gyro", gyro.getAngle());
        Scheduler.getInstance().run();
        actuator.move();
        tank.move();
        swerve.move((gyro.getAngle() * -.025), tank);
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
