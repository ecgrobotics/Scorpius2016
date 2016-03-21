
package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VMAbstraction extends IterativeRobot {
	
    public void robotInit() {
    	//Call Start across the board
    	Splicer.Initialize(false);
    	
    }
    
    public void autonomousInit() {
    	//Call Start across the board //Is this already taken care of by autonomousInit?
    	Splicer.Initialize(true);
    }

    public void autonomousPeriodic() {
    	//Call Update across the board
    	Scheduler.getInstance().run();
    	Splicer.Update(true);
    }

    public void teleopPeriodic() {
        //Call Update across the board
    	Scheduler.getInstance().run();
    	Splicer.Update(false);
    }
    
    public void testPeriodic() {
    
    }
    
}
