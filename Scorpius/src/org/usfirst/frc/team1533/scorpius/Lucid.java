package org.usfirst.frc.team1533.scorpius;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * @author Yusuf
*/
public class Lucid {
	
	//NIVision state vars
	static int session;
	static Image frame = null;
	
	public static void Initialize () { //UPDATE //New SmartDashboard logs
		//Create an image acquisition client
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        //Assign the session handler //UPDATE //ConstantFactory Camera Name
        session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        //Configure the camera for low-latency frame retrieval
        NIVision.IMAQdxConfigureGrab(session);
        //Start the acquisition
        NIVision.IMAQdxStartAcquisition(session);
	}
	
	public static void Update () {
		//Grab the current frame
		NIVision.IMAQdxGrab(session, frame, 1);
		//Get the size of the camera's view
		CameraServer.getInstance().setImage(frame);
	}
}
