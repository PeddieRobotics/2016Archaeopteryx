/*
 * This class records all the data in one Exel document
 * Also, it creates a new document with a new name everytime we run a Robot
 */


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
	private Turret turret;
	
	private boolean recordFile;
	private Formatter f;
	
	/*
	 * Constructor, gets the drive, the arm, flywheel and the turret as parameters
	 */
	
	public Recorder(Drive d, CDFArm a, Flywheel fy, Intake in, Turret t ){
		this.drive = d;
		this.arm = a;
		this.fly = fy;	
		this.intake = in;
		this.turret = t;
		recordFile = false;
	}
	
	/*
	 * This method creates a file with a new name and creates
	 * columns for data
	 */
	
	public void startRecording(String filename) {
    	try {
    		if (recordFile==false) {
    		f= new Formatter("/c/Logs/" + filename);
    		f.format("Time,DriveAngle,DriveDistance,ArmPositionUp,FlywheelRPM,IntakePositionUp,Ball,TurretAngle\r\n");
    		recordFile=true;
    		}
    	} catch (FileNotFoundException e) {
    		DriverStation.reportError(
    				"FileNotFoundException\n", true);
	 }
    }
    
	/*
	 * Closes the file 
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
    
    /*
     * This method writes the data into the created file
     */
    
    public void record() {
    	if (recordFile==true) {
    		f.format("%f,%f,%f,%b,%f,%b,%b,%f\r\n",
    				Timer.getFPGATimestamp(),
    				drive.getAngle(),
    				drive.getDistance(),
    				arm.getArmPosition(),
    				fly.getSpeed(),
    				intake.isDown(),
    				intake.hasBall(),
    				turret.getAngle());
    	}
    }
    
    /*
     * Reads a total number of files from another file, then
     * increments the number after every run
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
