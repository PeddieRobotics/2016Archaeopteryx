
package org.usfirst.frc.team5895.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

import org.usfirst.frc.team5895.robot.framework.Looper;
import org.usfirst.frc.team5895.robot.Drive;

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
	Looper updater;
	Looper recorder;
	
	int incCount;
	
    public void robotInit() {
    	
    	leftJoystick = new Joystick(0);
        rightJoystick = new Joystick(1);
    	
    	updater = new Looper(10);
    	recorder = new Looper(250);
    	drive = new Drive();
    	
    	updater.start();
    	recorder.start();
    	
    	incCount = incrementCount();
    }
    
    public void autonomousInit() {
    	drive.startRecording("autoDrive"+incCount+".csv");
    	drive.turnTo(180);
    	drive.stopRecording();
    }

    public void teleopInit() {
 	   drive.startRecording("teleopDrive"+incCount+".csv");
 	   
    }
    
    public void teleopPeriodic() {
    	drive.haloDrive(leftJoystick.getRawAxis(1),rightJoystick.getRawAxis(0));
    }
    
    public void disabledInit() {
    	drive.stopRecording();    	
    }
    
    private int incrementCount() {
    	try {
    		   Scanner sca;
    		sca = new Scanner(new File("//c/Logs//Count.txt"));
    		   int x = sca.nextInt();
    		   Formatter count;
    		   count = new Formatter("//c/Logs//Count.txt");
    		   count.format("%d", x+1);
    		   count.close();
    		   return x;
    	} catch (FileNotFoundException e) {
    		// TODO Auto-generated catch block
    		return -1;
    	}
       }
    
}
