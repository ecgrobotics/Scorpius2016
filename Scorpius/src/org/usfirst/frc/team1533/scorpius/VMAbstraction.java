
package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VMAbstraction extends IterativeRobot {
	
    public void robotInit() {
    	//Call Start across the board
    	Splicer.Start();
    }
    
    public void autonomousInit() {

    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
        //Call Update across the board
    	Splicer.Update();
    }
    
    public void testPeriodic() {
    
    }
    
}
