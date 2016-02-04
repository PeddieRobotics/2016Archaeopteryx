
package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.Looper;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Looper updater;
	Looper recorder;
	
    public void robotInit() {
    	updater = new Looper(10);
    	recorder = new Looper(250);
    	
    	
    	
    	updater.start();
    	recorder.start();
    }
    
    public void autonomousInit() {
    	
    }

    public void teleopInit() {
    	
    }
    
    public void teleopPeriodic() {
        
    }
    
    public void disabledInit() {
    	
    }
}
