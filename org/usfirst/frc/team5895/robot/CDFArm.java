package org.usfirst.frc.team5895.robot;

import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class CDFArm {

	Solenoid myCylinder;
	private boolean recordFile;
	Formatter f;
	
	public CDFArm(){
		myCylinder = new Solenoid(0);
	}
	
	public void up(){
		myCylinder.set(true);
	}
	public void down(){
		myCylinder.set(false);
	}
	
	public void startRecording(String filename){
		try {
    		if (recordFile==false){
    		f= new Formatter("/c/Logs/Drive//" + filename);
    		f.format("Time,Angle,Distance");
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
	
	
	}
