package org.usfirst.frc.team1533.scorpius;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * @author Duncan
 * Implementation of 6127V1A360L.5FS absolute encoder for use with swerve drive.
 */
public class AbsoluteEncoder extends AnalogInput {
	double angleOffset;
	boolean flipped = false;
	
	/**
	 * @param channel
	 * @param angleOffset zero point of encoder in degrees
	 */
	public AbsoluteEncoder(int channel, double angleOffset) {
		super(channel);
		this.angleOffset = Math.toRadians(angleOffset);
	}
	
	public AbsoluteEncoder(int channel, double angleOffset, boolean flipped) {
		super(channel);
		this.angleOffset = Math.toRadians(angleOffset);
		this.flipped = flipped;
	}
	
	/**
	 * @return angular position of encoder in radians (0 to 2pi)
	 */
	public double getAngle() {
		//convert voltage (0.2-4.8) to radians
		double angle;
		if (flipped) angle = (4.8 - getAverageVoltage()) * (2*Math.PI) / 4.6;
		else angle = (getVoltage() - 0.2) * (2*Math.PI) / 4.6;
		angle = (angle + angleOffset) % (2*Math.PI);
		while (angle < 0) angle += 2*Math.PI;
		return angle;
	}
	
	/**
	 * @return result of getAngle()
	 */
	public double pidGet() {
		return getAngle();
	}
	
}
