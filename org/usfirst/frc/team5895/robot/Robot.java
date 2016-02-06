
package org.usfirst.frc.team5895.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

import org.usfirst.frc.team5895.robot.framework.Looper;
import org.usfirst.frc.team5895.robot.Drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Joystick leftJoystick;
    Joystick rightJoystick;
	
	Drive drive;
	CDFArm arm;
	Flywheel flywheel;
	Looper updater;
	Looper r;
	
	Recorder recorder;
	
	int matchCount;
	
    public void robotInit() {
    	
    	leftJoystick = new Joystick(0);
        rightJoystick = new Joystick(1);
    	
    	updater = new Looper(10);
    	r = new Looper(250);
    	
    	drive = new Drive();
    	arm = new CDFArm();
    	flywheel = new Flywheel();
    	
     	recorder = new Recorder(drive,arm,flywheel);
    	
    	updater.start();
    	r.start();
    	matchCount = incrementCount();
    }
    
    public void autonomousInit() {
    	recorder.startRecording("auto"+matchCount+".csv");
    	drive.turnTo(180);
    	recorder.stopRecording();
    }

    public void teleopInit() {
 	   recorder.startRecording("teleop"+matchCount+".csv");
    }
    
    public void teleopPeriodic() {
    	//DRIVE
    	drive.haloDrive(leftJoystick.getRawAxis(1),rightJoystick.getRawAxis(0));
    	
    	//CDF ARM UP OR DOWN
    	if(leftJoystick.getRawButton(1)){
    		arm.up();
    	}
    	else if(leftJoystick.getRawButton(2)){
    		arm.down();
    	}
    	
    	//FLYWHEEL CONTROL SPEED
    	if(rightJoystick.getRawButton(3)){
    		flywheel.setSpeed(1.0);
    	}
    }
    
    
    public void disabledInit() {
    	recorder.stopRecording();  
    }
    
    private int incrementCount() {
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
