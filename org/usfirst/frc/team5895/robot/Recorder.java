package org.usfirst.frc.team5895.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;


public class Recorder {	
	private Drive drive;
	private CDFArm arm;
	private Flywheel fly;
	private Intake intake;
	
	private boolean recordFile;
	private Formatter f;
	
	/**
	 * Makes a new Recorder
	 * @param d the drivetrain 
	 * @param a the arm 
	 * @param fy the flywheel 
	 * @param in the intake
	 * @param t the turret
	 */
	public Recorder(Drive d, CDFArm a, Flywheel fy, Intake in){
		this.drive = d;
		this.arm = a;
		this.fly = fy;	
		this.intake = in;
		recordFile = false;
	}
	
	/**
	 * Creates a file for saving data and makes separate columns for everything
	 * @param filename The name of the log file to create, overwrites this file
	 * 			if it already exists
	 */
	public void startRecording(String filename) {
    	try {
    		if (recordFile==false) {
    		f= new Formatter("/c/Logs/" + filename);
    		f.format("Time,DriveAngle,DriveDistance,ArmPositionUp,FlywheelRPM,FlywheelUp,IntakePositionUp,Ball,TurretAngle,\r\n");
    		recordFile=true;
    		}
    	} catch (FileNotFoundException e) {
    		DriverStation.reportError(
    				"FileNotFoundException\n", true);
	 }
    }
    
	/**
	 * Stops recording and closes the file
	 */
    public void stopRecording(){
    	try {
    		if (recordFile==true){
    			f.close();
    			recordFile=false;
    		}
    	} catch (FormatterClosedException e) {
    		DriverStation.reportError(
    				"FormatterClosedException\n", true);
    	}
    }
    
    /**
     * Writes the data into assigned columns 
     */
    public void record() {
    	if (recordFile==true) {
    		f.format("%f,%f,%f,%b,%f,%b,%b,%b\r\n",
    				Timer.getFPGATimestamp(),
    				drive.getAngle(),
    				drive.getDistance(),
    				arm.getArmPosition(),
    				fly.getSpeed(),
    				fly.getUpDown(),
    				intake.isDown(),
    				intake.hasBall());
    	}
    }
    
    /**
     * Reads the number of files from another file, then increments it
     * @return the number of the file
     */
    public int incrementCount() {
    	try {
    		   Scanner sca;
    		   sca = new Scanner(new File("/c/Logs/Count.txt"));
    		   int x = sca.nextInt();
    		   Formatter count;
    		   count = new Formatter("/c/Logs/Count.txt");
    		   count.format("%d", x+1);
    		   count.close();
    		   sca.close();
    		   return x;
    	} catch (FileNotFoundException e) {
    		DriverStation.reportError("FileNotFoundExeption\n", true);
    		return -1;
    	}
       }
}
