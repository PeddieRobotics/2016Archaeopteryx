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
	private Intake intake;
	private Turret turret;
	
	private boolean recordFile;
	Formatter f = new Formatter();
	
	public Recorder(Drive d, CDFArm a, Flywheel fy, Intake in, Turret t ){
		this.drive = d;
		this.arm = a;
		this.fly = fy;	
		this.intake = in;
		this.turret = t;
	}
	
	public void startRecording(String filename) {
    	try {
    		if (recordFile==false) {
    		f= new Formatter("/c/Logs/" + filename);
    		f.format("Time,DriveAngle,DriveDistance,ArmPositionUp,FlywheelRPM,IntakePositionUp,Intaking,Ball\r\n");
    		recordFile=true;
    		}
    	} catch (FileNotFoundException e) {
    		DriverStation.reportError(
    				"File not Found Exception in Recorder::startRecording\n", false);
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
    				"Formatter Closed Exception in Recorder::stopRecording\n", false);
    	}
    }
    
    public void record() {
    	if (recordFile==true) {
    		f.format("%f,%f,%f,%b,%f,%b,%b,%b,%f\r\n",
    				Timer.getFPGATimestamp(),
    				drive.getAngle(),
    				drive.getDistance(),
    				arm.getArmPosition(),
    				fly.getSpeed(),
    				intake.getUpDown(),
    				intake.getIntaking(),
    				intake.getBall());
    				turret.getAngle();
    	}
    }
}
