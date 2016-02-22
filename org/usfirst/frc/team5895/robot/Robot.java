
package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.Looper;
import org.usfirst.frc.team5895.robot.Drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;


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
	
	TalonSRX intakeMotor;
	Solenoid intakeSol;
	TalonSRX turMotor;
	
	TalonSRX topFlywheelMotor;
	TalonSRX bottomFlywheelMotor;
	Solenoid flywheelSolenoid;
	
    public void robotInit() {
    	
    	leftJoystick = new Joystick(0);
        rightJoystick = new Joystick(1);
    	
    	u = new Looper(10);
    	r = new Looper(250);
    	
    	drive = new Drive();
    	arm = new CDFArm();
    	flywheel = new Flywheel();
    	//intake = new Intake();
    	turret = new Turret();
    	
     	//recorder = new Recorder(drive,arm,flywheel,intake,turret);
    	//matchCount = recorder.incrementCount();
     	
    	//u.add(intake::update);
    	u.add(drive::update);
    	
    	intakeMotor = new TalonSRX(ElectricalLayout.INTAKE_MOTOR);
    	intakeSol = new Solenoid(ElectricalLayout.INTAKE_SOLENOID);
    	turMotor = new TalonSRX(ElectricalLayout.TURRET_MOTOR);
    	
    	topFlywheelMotor = new TalonSRX(5);
    	bottomFlywheelMotor = new TalonSRX(4);
    	flywheelSolenoid = new Solenoid(ElectricalLayout.FLYWHEEL_SOLENOID);
     	
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
    	
    	DriverStation.reportError("flywheel RPM" + flywheel.getSpeed() +"\n", false);
    	
    	if (leftJoystick.getRawButton(3)) {
    		flywheel.down();
    	} else if (leftJoystick.getRawButton(4)) {
    		flywheel.up();
    	}
    	
    	if(leftJoystick.getRawButton(11)){
    		flywheel.setSpeed(100);
    	} else if(leftJoystick.getRawButton(12)){
    		flywheel.setSpeed(1000);
    	} else if(leftJoystick.getRawButton(13)) {
    		flywheel.setSpeed(0);
    	}
    	
    	
    	if (rightJoystick.getRawButton(3)) {
    		intakeMotor.set(0.5);
    	} else { 
    		intakeMotor.set(0);
    		}
    	
    	if(leftJoystick.getRawButton(1)){
    		intakeSol.set(true);
    	}
    	else if(leftJoystick.getRawButton(2)){
    		intakeSol.set(false);
    	}
    	
    	if(rightJoystick.getRawButton(1)){
    		turMotor.set(0.1);
    	}
    	else if(rightJoystick.getRawButton(2)){
    		turMotor.set(-0.1);
    	}
    	else {turMotor.set(0);}
    	
    	
    	
    	
    	
    	
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
    	
    	//FLYWHEEL CONTROL SPEED
    	if(rightJoystick.getRawButton(3)){
    		flywheel.setSpeed(1.0);
    	}
    	*/
    	//INTAKE UP OR DOWN
    	/*
    	if(leftJoystick.getRawButton(1)){
    		intake.up();
    	}
    	else if(leftJoystick.getRawButton(2)){
    		intake.down();
    	}
    	
    	
    	//SHOOTING
    	if(rightJoystick.getRawButton(1)){
    		intake.shoot();
    	}
    	*/
    	
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
    }  
}
