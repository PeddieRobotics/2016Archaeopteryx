package org.usfirst.frc.team5895.robot;

import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Recorder {	
	private Drive drive;
	private CDFArm arm;
	private Flywheel fly;
	
	private boolean recordFile;
	Formatter f = new Formatter();
	
	public Recorder(Drive d, CDFArm a, Flywheel fy ){
		this.drive = d;
		this.arm = a;
		this.fly = fy;	
	}
	
	public void startRecording(String filename) {
    	try {
    		if (recordFile==false){
    		f= new Formatter("/c/Logs/Drive//" + filename);
    		f.format("Time,DriveAngle,DriveDistance,ArmPositionUp,FlywheelRPM");
    		recordFile=true;
    		}
    	} catch (FileNotFoundException e) {
    		DriverStation.reportError(
    				"File not Found Exception in Drive::startRecording\n", false);
	 }
    }
    
    public void stopRecording(){
    	try {
    		if (recordFile==true){
    			f.close();
    			recordFile=false;
    		}
    	} catch (FormatterClosedException e) {
    		DriverStation.reportError(
    				"Formatter Closed Exception in Drive::stopRecording\n", false);
    	}
    }
    
    public void record() {
    	if (recordFile==true){
    		f.format("\r\n%f,%f,%f,%b,%f", Timer.getFPGATimestamp(), drive.getAngle(), drive.getDistance(), arm.getArmPosition(), fly.getSpeed());
    	}
    }
}
