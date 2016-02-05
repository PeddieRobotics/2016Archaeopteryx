package org.usfirst.frc.team5895.robot;

import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class CDFArm {

	Solenoid myCylinder;
	
	boolean getPosition;
	boolean recordFile;
	
	Formatter fm;
	
	public CDFArm(){
		myCylinder = new Solenoid(0);
	}
	
	public void up(){
		myCylinder.set(true);
		getPosition = true;
	}
	public void down(){
		myCylinder.set(false);
		getPosition = false;
	}
	
	public void startRecording(String filename) {
    	try {
    		if (recordFile==false){
    		fm= new Formatter("/c/Logs/Drive//" + filename);
    		fm.format("Time,Up?");
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
    			fm.close();
    			recordFile=false;
    		}
    	} catch (FormatterClosedException e) {
    		DriverStation.reportError(
    				"Formatter Closed Exception in Drive::stopRecording\n", false);
    	}
    }
	
	public void record() {
    	if (recordFile==true){
    		fm.format("\r\n%f,%b", Timer.getFPGATimestamp(), getPosition);
    	}
    }
	
}
