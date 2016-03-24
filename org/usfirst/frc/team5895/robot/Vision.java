package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Vision {
	
	public Vision() 
	{
		try {
			USBCamera c = new USBCamera("cam1");
			c.setBrightness(0);
			c.setExposureManual(-8);
        	CameraServer server = CameraServer.getInstance();
        	server.startAutomaticCapture(c);
		} catch (Exception e) {
			DriverStation.reportError("No Camera!", false);
		}
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
}
