package org.usfirst.frc.team5895.robot;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class NewVision {

	USBCamera cam0;
	USBCamera cam1;
	Image frame0;
	Image frame1;
	CameraServer server = CameraServer.getInstance();
	boolean camNumber = false;
	
	public NewVision(){
    	try {
    		cam0 = new USBCamera("cam0");
    	} catch (Exception e){
    		DriverStation.reportError("No Camera 0", false);
    	}
		try {
			cam1 = new USBCamera("cam1");
			cam1.setBrightness(0);
			cam1.setExposureManual(-8);
		} catch (Exception e) {
			DriverStation.reportError("No Camera 1!", false);
		}
	}
	
	public void cam0(){
		cam1.stopCapture();
		cam0.startCapture();
		camNumber = false;
		SmartDashboard.putBoolean("DB/LED 1", true);
	}
	
	public void cam1(){
		cam0.stopCapture();
		cam1.startCapture();
		camNumber = true;
		SmartDashboard.putBoolean("DB/LED 1", false);
	}
	
	public double getX() {
		return SmartDashboard.getNumber("DB/Slider 0", 0);
	}
	
	public boolean hasTarget(){
		return SmartDashboard.getBoolean("DB/LED 0", false);
	}
	
	public double getY() {
		return SmartDashboard.getNumber("DB/Slider 1", 0);
	}
	
	public void update(){
		if(camNumber){
			cam1.getImage(frame1);
		server.setImage(frame1);
		}
		else {
			cam0.getImage(frame0);
			server.setImage(frame0);
		}
	}
}
