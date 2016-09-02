package org.usfirst.frc.team1533.robot.subsystems;


import org.usfirst.frc.team1533.robot.ConstantFactory;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Duncan
 *
 */
public class SwerveModule {
    PIDController steerPID;
    SpeedController steerController, driveController; //SpeedController used so this can be talon, victor, jaguar, CAN talon...
    public AbsoluteEncoder steerEncoder;
    double positionX, positionY; //position of this wheel relative to the center of the robot
    //from the robot's perspective, +y is forward and +x is to the right
    boolean enabled = false;
    
    /**
     * @param driveController motor controller for drive motor
     * @param steerController motor controller for steer motor
     * @param steerEncoder absolute encoder on steering motor
     * @param positionX x coordinate of wheel relative to center of robot (inches)
     * @param positionY y coordinate of wheel relative to center of robot (inches)
     */
    public SwerveModule(SpeedController driveController, SpeedController steerController, 
    		AbsoluteEncoder steerEncoder, double positionX, double positionY) {
    	this.steerController = steerController;
    	this.driveController = driveController;
    	this.steerEncoder = steerEncoder;
    	this.positionX = positionX;
    	this.positionY = positionY;
    	steerPID = new PIDController(ConstantFactory.Steering.SWERVE_STEER_P, ConstantFactory.Steering.SWERVE_STEER_I, ConstantFactory.Steering.SWERVE_STEER_D,
    			steerEncoder, steerController);
    	steerPID.setInputRange(0, 2*Math.PI);
    	steerPID.setOutputRange(-ConstantFactory.Steering.SWERVE_STEER_CAP, ConstantFactory.Steering.SWERVE_STEER_CAP);
    	steerPID.setContinuous();
    	steerPID.disable();
    }
    
    public void enable() {
    	steerPID.enable();
    	enabled = true;
    }
    
    public void disable() {
    	steerPID.disable();
    	driveController.set(0);
    	steerController.set(0);
    	enabled = false;
    }
    
    /**
     * @param angle in radians
     * @param speed motor speed [-1 to 1]
     */
    public void set(double angle, double speed) {
    	if (!enabled) return;
    	angle = wrapAngle(angle);
    	double dist = Math.abs(angle-steerEncoder.getAngle());
    	//if the setpoint is more than 90 degrees from the current position, flip everything
    	if (dist > Math.PI/2 && dist < 3*Math.PI/2) {
    		angle = wrapAngle(angle + Math.PI);
    		speed *= -1;
    	}
    	steerPID.setSetpoint(angle);
    	driveController.set(Math.max(-1, Math.min(1, speed))); //coerce speed between -1 and 1
    }
    
    public void rest() {
    	driveController.set(0);
    }
    public double getAngle(){
    	return steerEncoder.getAngle();
    }
    
    private double wrapAngle(double angle) {
    	angle %= 2*Math.PI;
    	if (angle<0) angle += 2*Math.PI;
    	return angle;
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}