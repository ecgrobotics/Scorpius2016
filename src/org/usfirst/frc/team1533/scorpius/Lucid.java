//package org.usfirst.frc.team1533.scorpius;
//
//import com.ni.vision.NIVision;
//import com.ni.vision.NIVision.Image;
//
//import edu.wpi.first.wpilibj.CameraServer;
//
//public class Lucid {
//	static int session;
//	static Image frame;
//	
//	public static void Initialize(){
//		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
//		try {
//			session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
//			NIVision.IMAQdxConfigureGrab(session);
//			NIVision.IMAQdxStartAcquisition(session);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	
//	public static void Update(){
//		try {
//			NIVision.IMAQdxGrab(session, frame, 1);
//			CameraServer.getInstance().setImage(frame);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//}
