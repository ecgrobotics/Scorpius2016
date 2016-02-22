package org.usfirst.frc.team1533.scorpius;

import java.util.ArrayList;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Yusuf
*/
public class Lucid {
	
	//The Lucid algorithm
	//The algorithm's goal is to find the goal's position in relation to the robot, so
	//that the robot can translate and rotate to score a boulder in the goal.
	
	//The algorithm can be broken into major steps:
	//1. Identifying the targets
	//2. Mathematically choosing the best target to attempt
	//   [Driver presses 'auto-shoot' button]
	//3. Robot positions itself correctly to make the goal
	//4. Robot calculates an elevation term for the cannon
	//5. Robot shoots the ball
	
	//The algorithm (and hopefully the entire codebase) would work on a callback/
	//delegate execution pattern. This would be most efficient considering the 
	//modest architecture of the roboRIO's ARM processor.
	
	//The control flow would be as follows:
	//Target recognition -> Sensory passes control to Lucid -> Lucid positions the robot
	// -> Lucid shoots -> Lucid passes control back to Sensory.
	
	//***The particle identification stage of the algorithm is adapted from the 
	//2015 Retro Vision Example
	
	//The four steps of the algorithm are further broken down:
	//1. Identifying the targets:
	//
	//   Preprocessing will be performed on the camera:
	//		-LED rings will be placed around the camera lens to achieve retro-reflectivity.
	//   	-The camera will return its preview buffers in the HSV colorspace so that NI Vision
	//    	 can perform threshold-masking ops.
	//   	-The camera will be calibrated to reduce its brightness/exposure significantly.
	//
	//   Then during runtime, identification will be performed as follows:
	//a. Pixels will be masked based on one or two planes of the HSV colorspace.
	//	 The planes used will be dependent on the LED rings and thresholding.
	//	 This should yield a frame with several particles, some which conform to
	//	 the target profile more than others.
	//b. The masked frame will be analyzed for particles. This will be done by using
	//	 a pre-made particle filter criterion based on the aspect ratio of the particle's
	//	 bounding rect in normalzed viewport coordinates (which is not distance-dependent).
	//	 After small particles have been filtered out, processing will continue if there
	//	 are any possible targets left.
	
	//2. Once we are left with aspect ratio conforming particles (with little marginal error),
	//	 we would retain a particle P which has the most ideal aspect ratio and size.
	
	//3. When we have P, we will move the robot in a circular locus, pivoted at the 
	//	 particle P. When the robot has an aspect ratio of P that is nearly ideal, the
	//	 robot will stop rotating about P and will begin elevation calculations for shooting.
	
	//4. To calculate the elevation term, we must make some assumptions:
	//	 -The ball will enter the goal at the maximum height and half range of its
	//	  parabolic trajectory
	//	 -The ball faces no air resistance
	//	 -The cannon's muzzle velocity is constant across ALL shots
	//	 -The camera has a fixed view facing forward (and it's forward vector is parallel to ground)
	//	 -The camera's vertical field of view is known and constant
	//	 -The distance from the camera to particle P is known
	//	 
	//	 We will be looking for an angle, [T], which will satisfy the equation of the ball's
	//	 trajectory. We can either satisfy the equation of the half-range of the trajectory,
	//	 or the maximum height. This explanation will walk through the range satisfaction,
	//	 even though the maximum height satisfaction might be more reliable.
	//
	//	 We start with what we know: the normalized viewport y coordinate of the tower's
	//	 particle's moment of inertia, [y], and the distance of [y] from the camera, [r],
	//	 which is derived from the CV term. We use the following formula to conform the
	//	 ball to a trajectory that will enter the goal at its half range (which should be
	//	 the point where the height is maximum):
	//	 x = 1/2R = ( u*u * sin(2*T) ) / (2*g)
	//	 where [x] is the horizontal distance from the robot to the foot of the tower.
	//	 In order for us to continue, we need to find [x]. We can deduce [x] by transforming
	//	 our normalized viewport coordinate to an angular displacement from zero degrees
	//	 respecting an imaginary far clipping plane. We do this by flattening our camera's 
	//	 view to the YZ plane. This yields a right triangle with opposite [h], 
	//	 hypotenuse [r], and adjacent [x] which is what we need. [r] is the distance from the 
	//	 camera to the target gotten from the CV term. When the opposite is maximum, 1.0 in 
	//	 normalized viewport coordinates, the angle between the adjacent and the hypotenuse
	//	 is half of the camera's vertical field of view. Thus, we can transpose this angle-
	//	 coordinate relationship to get the angle from the camera's view to the tower:
	//	 1/r = tan (FOV(y) * 1/2),
	//	 a = arctan (y/r),
	//	 a = arctan (y * tan (FOV(y) * 1/2))
	//	 where a is the angle from the camera to the tower goal, where y is the  normalized
	//	 viewport y coordinate of the tower's particle's moment of inertia.
	//	 Now that we have the angle of elevation, we can find the horizontal distance
	//	 between the robot and the tower by using the simple relationship:
	//	 x = r * cos(a)
	//	 Now we can apply our distance formula to the projectile motion formula as follows:
	//	 x = 1/2R = r * cos(a) = ( u*u * sin(2*T) ) / (2*g),
	//	 sin (2*T) = 2g * r * cos(a) / (u*u)
	//	 T = (1/2) * arcsin (2g * r * cos(arctan (y * tan ( (1/2) * FOV(y))))).
	//	 Keep in mind that [y] must be transposed from [0,1] to [-1, 1]. This could be
	//	 achieved by the following:
	//	 yRect = y * 2 - 1
	
	//5. The robot should now be correctly oriented for a clean shot. The robot will make
	//	 the shot, then pass control back to Sensory.
	
	
	//NIVision state vars
	static int session;
	static Image frame = null;
	static Image maskedFrame = null;
	static NIVision.GetImageSizeResult frameSize = null;
	static int imaqError;
	//Vision ops vars
	static NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	static NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
	public static void Initialize () { //UPDATE //New SmartDashboard logs
		//Create an image acquisition client
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		//Create the image containing masked particles
		maskedFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		//Create the masking criterion
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, ConstantFactory.Photoduct.AREA_MINIMUM, 100.0, 0, 0);
        //Assign the session handler //UPDATE //ConstantFactory Camera Name
        session = NIVision.IMAQdxOpenCamera(ConstantFactory.Camera.NAME, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        //Configure the camera for low-latency frame retrieval
        NIVision.IMAQdxConfigureGrab(session);
        //Start the acquisition
        NIVision.IMAQdxStartAcquisition(session);
	}
	
	public static void Update () {
		//Grab the current frame
		NIVision.IMAQdxGrab(session, frame, 1);
		//Get the size of the camera's view
		frameSize = NIVision.imaqGetImageSize(frame);
		//Threshold the image with the HSV planes
		NIVision.imaqColorThreshold(maskedFrame, frame, 255, NIVision.ColorMode.HSV, ConstantFactory.Photoduct.HUE_RANGE, ConstantFactory.Photoduct.SAT_RANGE, ConstantFactory.Photoduct.VAL_RANGE);
		//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(maskedFrame, 1);
		//Log
		SmartDashboard.putNumber("Masked particles", numParticles);
		//Send masked image to dashboard to assist in tweaking mask.
		CameraServer.getInstance().setImage(maskedFrame);
		//Filter the particles in the mask with the set criterion
		imaqError = NIVision.imaqParticleFilter4(maskedFrame, maskedFrame, criteria, filterOptions, null);
		//Get the numbers of particles still standing
		numParticles = NIVision.imaqCountParticles(maskedFrame, 1);
		//Send filtered particle count to the dash
		SmartDashboard.putNumber("Filtered particles", numParticles);
		//Sort throught the particles and perform Lucid Ops
		if(numParticles > 0) {
			//Get the detected objects
			ArrayList<DetectableObject> detectedObjects = EnumerateParticles(numParticles);
			//Iterate over objects and draw a rect around them
			for (int i = 0; i < detectedObjects.size(); i++) {
				//Assign local
				DetectableObject obj = detectedObjects.get(i);
				
				//DEBUG
				String nameToken = "Particle "+i;
				SmartDashboard.putString(nameToken, obj.Centre().ToString());
				SmartDashboard.putNumber(nameToken+" Distance", obj.GetDistance()/2); //FIX // /2
				
				//Draw
				//NIVision.imaqDrawShapeOnImage(frame, frame, obj.ScreenSpaceRect(Vector2.Parse(frameSize)), DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
			}
		}
		//CameraServer.getInstance().setImage(frame);
	}
	
	public static ArrayList<DetectableObject> EnumerateParticles (int numParticles) {
		//Create a return collection
		ArrayList<DetectableObject> ret = new ArrayList<DetectableObject>();
		//Enumerate the particles
		for (int i = 0; i < numParticles; i++) {
			//Get each particle's coordinates
			double top = NIVision.imaqMeasureParticle(maskedFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP) / frameSize.height;
			double left = NIVision.imaqMeasureParticle(maskedFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT) / frameSize.width;
			double bottom = NIVision.imaqMeasureParticle(maskedFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM) / frameSize.height;
			double right = NIVision.imaqMeasureParticle(maskedFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT) / frameSize.width;
			//Create a detectable object
			DetectableObject obj = new DetectableObject(bottom, left, top, right);
			//Add it to the return collection
			ret.add(obj);
		}
		return ret;
	}
	
	public static double GetElevation (double CV_Distance, double Viewport_Y) { //DEPLOY //CHECK //Radians and degrees
		//Define elevation
		double T = 0;
		//Transpose [y] from [0,1] to [-1, 1]
		double y = Viewport_Y * 2 - 1;
		//Calculate camera angle term
		double camTerm = y * Math.tan(0.5d * Math.toRadians(ConstantFactory.Lucid.CAMERA_FOV_Y) );
		//Calculate parabolic trajectory term
		double parabola = 2d * ConstantFactory.Lucid.GRAVITY * CV_Distance * Math.cos(Math.atan(camTerm));
		//Calculate the elevation
		T = 0.5d * Math.asin(parabola);
		//Return the elevation
		return T;
	}
}
