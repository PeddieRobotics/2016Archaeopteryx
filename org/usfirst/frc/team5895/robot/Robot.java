
package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.Looper;
import org.usfirst.frc.team5895.robot.framework.Waiter;
import org.usfirst.frc.team5895.robot.Drive;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.vision.USBCamera;


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
	Intake intake;
	Looper u;
	Looper r;
	
	Recorder recorder;
	
	int matchCount;
	boolean shooting;
	

//	TalonSRX turMotor;
	
	
    public void robotInit() {
    	
    	leftJoystick = new Joystick(0);
        rightJoystick = new Joystick(1);
    	
    	u = new Looper(10);
    	r = new Looper(250);
    	
    	drive = new Drive();
//    	arm = new CDFArm();
    	flywheel = new Flywheel();
    	intake = new Intake();
    	
    	
     	//recorder = new Recorder(drive,arm,flywheel,intake,turret);
    	//matchCount = recorder.incrementCount();
        
        shooting = false;
     	
    	u.add(intake::update);
    	u.add(drive::update);
    	u.add(flywheel::update);
  //  	u.add(turret::update);
    	
//    	turMotor = new TalonSRX(ElectricalLayout.TURRET_MOTOR);
     	
    	u.start();
    	r.start();
    }
    
    @Override
    public void autonomousInit() {
    	//recorder.startRecording("auto"+matchCount+".csv");
    	//drive.turnTo(180);
    	//recorder.stopRecording();

    	
   // 	drive.turnTo(drive.getAngle() + 45);
   // 	Waiter.waitFor(drive::atAngle, 2000);
   // 	drive.haloDrive(0, 0);
    }

    public void teleopInit() {
 	  // recorder.startRecording("teleop"+matchCount+".csv");
    }
    
    public void teleopPeriodic() {
    	
    	
//    	if (leftJoystick.getRawButton(3)) {
//    		flywheel.down();
//    	} else if (leftJoystick.getRawButton(4)) {
//    		flywheel.up();
//    	}

    	
    	//DRIVE
    	if (Math.abs(leftJoystick.getRawAxis(1)) > 0.1 ||
    			Math.abs(rightJoystick.getRawAxis(0)) > 0.1) {
    		shooting = false;
    		flywheel.setSpeed(0);
   			drive.haloDrive(leftJoystick.getRawAxis(1),rightJoystick.getRawAxis(0));
    	} else {
    		if (shooting == false) {
    			drive.haloDrive(0,0);
    		}
    	}
    	    	
    	//INTAKE UP OR DOWN
    	if(leftJoystick.getRawButton(2)){
    		intake.up();
    	}
    	else if(leftJoystick.getRawButton(1)){
    		intake.down();
    	}
    	
    	
    	//SHOOTING
    	if(rightJoystick.getRawButton(1)){
    //		drive.visionTurn();
    		flywheel.setSpeed(2900);
    		shooting = true;
    	}
    	if (shooting && flywheel.atSpeed()) {
    		intake.shoot();
    		shooting = false;
    	}
    	
    }
    
    public void disabledInit() {
    	//recorder.stopRecording(); 
    	intake.up();
    	flywheel.setSpeed(0);
    }  
}
