package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Vision {
	
	USBCamera c = new USBCamera("cam1");
	
	public Vision() 
	{
		c.setBrightness(0);
        c.setExposureManual(-8);
        CameraServer server = CameraServer.getInstance();
        server.startAutomaticCapture(c);
	}
	
	public double getX() {
		return SmartDashboard.getNumber("DB/Slidero", 0);
	}
}
