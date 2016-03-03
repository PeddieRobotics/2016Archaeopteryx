
package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.Looper;
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
	Turret turret;
	Intake intake;
	Looper u;
	Looper r;
	
	Recorder recorder;
	
	int matchCount;
	

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
    	turret = new Turret();
    	

        USBCamera c = new USBCamera("cam1");
        c.setBrightness(0);
        c.setExposureManual(-8);
        CameraServer server = CameraServer.getInstance();
        server.startAutomaticCapture(c);
    	
     	//recorder = new Recorder(drive,arm,flywheel,intake,turret);
    	//matchCount = recorder.incrementCount();
     	
    	u.add(intake::update);
    	u.add(drive::update);
    	u.add(flywheel::update);
    	u.add(turret::update);
    	
//    	turMotor = new TalonSRX(ElectricalLayout.TURRET_MOTOR);
    	
     	
    	u.start();
    	r.start();
    }
    
    public void autonomousInit() {
    	//recorder.startRecording("auto"+matchCount+".csv");
    	//drive.turnTo(180);
    	//recorder.stopRecording();
    }

    public void teleopInit() {
 	  // recorder.startRecording("teleop"+matchCount+".csv");
    }
    
    public void teleopPeriodic() {
    	
    	DriverStation.reportError("flywheel RPM" + flywheel.getSpeed() + "NavX Angle" + drive.getAngle() +"\n", false);
    	
//    	if (leftJoystick.getRawButton(3)) {
//    		flywheel.down();
//    	} else if (leftJoystick.getRawButton(4)) {
//    		flywheel.up();
//    	}
    	
    	
    	if(leftJoystick.getRawButton(3)){
    		turret.set(-10);
    	}
    	else if(leftJoystick.getRawButton(4)){
    		turret.set(10);
    	}
    	else if(leftJoystick.getRawButton(2)){
    		turret.set(0);
    	}
    	
    	

    	
    	
    	
    	//DRIVE
    	drive.haloDrive(leftJoystick.getRawAxis(1),rightJoystick.getRawAxis(0));
    	
    	/*
    	//CDF ARM UP OR DOWN
    	if(leftJoystick.getRawButton(1)){
    		arm.up();
    	}
    	else if(leftJoystick.getRawButton(2)){
    		arm.down();
    	}
    	*/
    	//FLYWHEEL CONTROL SPEED
    	if(rightJoystick.getRawButton(4)){
    		flywheel.setSpeed(3000);
    	}
    	else if(rightJoystick.getRawButton(3)){
    		flywheel.setSpeed(0);
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
    		intake.shoot();
    	}
    	
    	/*
    	//SET TURRET
    	//STRAIGHT
    	if(leftJoystick.getRawButton(3)){
    		turret.set(0);
    	}
    	//CORNER
    	if(leftJoystick.getRawButton(4)){
    		turret.set(100);
    	}
    	//OTHER CORNER
    	if(leftJoystick.getRawButton(5)){
    		turret.set(-100);
    	}
    	*/
    }
    
    public void disabledInit() {
    	//recorder.stopRecording(); 
    	intake.up();
    	flywheel.setSpeed(0);
    }  
}
