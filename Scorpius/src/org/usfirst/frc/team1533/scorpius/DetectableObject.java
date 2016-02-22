package org.usfirst.frc.team1533.scorpius;

import com.ni.vision.NIVision;

public class DetectableObject {
	
	public double bottom;
	public double left;
	public double top;
	public double right;
	
	public DetectableObject (double Bottom, double Left, double Top, double Right) {
		bottom = Bottom;
		left = Left;
		top = Top;
		right = Right;
	}
	
	public Vector2 Centre () {
		return new Vector2((left+right)/2, (bottom+top)/2);
	}
	
	public double GetDistance () {
		double ret = 0;
		
		//Arc-Tangent model
		//ret = ConstantFactory.Lucid.TARGET_WIDTH / (Math.tan(Math.toRadians(0.5 * ConstantFactory.Lucid.CAMERA_FOV_X)) * (right - left)); //RADIANS
		
		//Arc-Length Ratio model //Much more versatile and accurate
		ret = (ConstantFactory.Lucid.TARGET_WIDTH * (ConstantFactory.Lucid.CAMERA_VIEWPORT_X_MAX - ConstantFactory.Lucid.CAMERA_VIEWPORT_X_MIN) * 360.0) / ((right-left) * ConstantFactory.Lucid.CAMERA_FOV_X * 2.0 * Math.PI);
		
		//Return it
		return ret;
	}
	
	public NIVision.Rect ScreenSpaceRect (Vector2 frameSize) {
		return new NIVision.Rect((int)Math.round(top * frameSize.y), (int)Math.round(left * frameSize.x), (int)Math.round((top-bottom) * frameSize.y), (int)Math.round((right-left) * frameSize.x));
	}
}
