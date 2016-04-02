
package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.BetterJoystick;
import org.usfirst.frc.team5895.robot.framework.Looper;
import org.usfirst.frc.team5895.robot.framework.Waiter;
import org.usfirst.frc.team5895.robot.Drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	BetterJoystick leftJoystick;
    BetterJoystick rightJoystick;
	
	Drive drive;
	CDFArm arm;
	Flywheel flywheel;
	Intake intake;
	Looper u;
	Looper r;
	Vision vision;
	
	Recorder recorder;
	
	int matchCount;
	boolean visionTurn;
	boolean shooting;
	

//	TalonSRX turMotor;
	
	
    public void robotInit() {
    	
    	leftJoystick = new BetterJoystick(0);
        rightJoystick = new BetterJoystick(1);
    	
    	u = new Looper(10);
    	r = new Looper(250);
    	
    	drive = new Drive();
//    	arm = new CDFArm();
    	flywheel = new Flywheel();
    	intake = new Intake();
    	vision = new Vision();
    	
    	
     	recorder = new Recorder(drive,arm,flywheel,intake);
    	matchCount = recorder.incrementCount();
        
        visionTurn = false;
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
    	
    	String defense = SmartDashboard.getString("DB/String 0");
    	String position = SmartDashboard.getString("DB/String 1");
    	
    	double angle = drive.getAngle();
    	
    	flywheel.up();
    	Waiter.waitFor(50);
    	
    	if (defense.contains("rough")) {
    		drive.driveVoltage(0.6, angle);
        	Waiter.waitFor(2500);
        	drive.haloDrive(0, 0);
    	} else if (defense.contains("rock")) {
    		drive.driveVoltage(0.6, angle);
        	Waiter.waitFor(2700);
        	drive.haloDrive(0, 0);
    	}
    	
    	
    	if (position.contains("2")) {
    		
    		Waiter.waitFor(200);
    		flywheel.setSpeed(2700);
    		
    		drive.turnTo(angle);
    		Waiter.waitFor(2000);
    		drive.haloDrive(0,0);
    	
    		drive.driveVoltage(0.35, angle);
    		Waiter.waitFor(1400);
    		drive.haloDrive(0, 0);
    		
    		Waiter.waitFor(100);
    		drive.turnTo(angle+35);
    		Waiter.waitFor(2500);
    		drive.haloDrive(0,0);
    		
    		/*Waiter.waitFor(100);
    		drive.driveVoltage(-0.3, angle+35);
    		Waiter.waitFor(1000);
    		drive.haloDrive(0,0);
    		*/
    	
    	} else if (position.contains("3")) {
    		
    		Waiter.waitFor(200);
    		flywheel.setSpeed(2725);
    		drive.turnTo(angle+5);
    		Waiter.waitFor(2000);
    		drive.haloDrive(0,0);
    		
    	} else if (position.contains("4")) {
    		
    		Waiter.waitFor(200);
    		flywheel.setSpeed(2725);
    		drive.turnTo(angle-2);
    		Waiter.waitFor(2000);
    		drive.haloDrive(0,0);
    		
    	} else if (position.contains("5")) {
    		
    		Waiter.waitFor(200);
    		flywheel.setSpeed(2700);
    		drive.turnTo(angle);
    		Waiter.waitFor(2000);
    		drive.haloDrive(0,0);
    	
    		drive.driveVoltage(0.35, angle);
    		Waiter.waitFor(1400);
    		drive.haloDrive(0, 0);
    		
    		Waiter.waitFor(100);
    		drive.turnTo(angle-35);
    		Waiter.waitFor(3000);
    		drive.haloDrive(0,0);
    		
    		/*Waiter.waitFor(100);
    		drive.driveVoltage(-0.3, angle-35);
    		Waiter.waitFor(500);
    		drive.haloDrive(0,0);
    		*/    	
    	}
    	
    	drive.visionTurn();
    	Waiter.waitFor(3000);
    	Waiter.waitFor(flywheel::atSpeed, 1000);
    	drive.haloDrive(0, 0);
    	intake.shoot();
    }

    public void teleopInit() {
 	  // recorder.startRecording("teleop"+matchCount+".csv");
    }
    
    public void teleopPeriodic() {
    	

    	if (rightJoystick.getRisingEdge(3)) {
    		flywheel.down();
    	} else if (rightJoystick.getRisingEdge(4)) {
    		flywheel.up();
    	}

    	
    	//DRIVE
    	if (!visionTurn) {
    		drive.haloDrive(leftJoystick.getRawAxis(1),rightJoystick.getRawAxis(0));
    	}
    	    	
    	//INTAKE UP OR DOWN
    	if(leftJoystick.getRisingEdge(2)){
    		intake.up();
    	}
    	else if(leftJoystick.getRisingEdge(1)){
    		intake.down();
    	}
    	
    	//INTAKE IN OR OUT
    	if(rightJoystick.getRisingEdge(2)){
    		intake.out();
    	}
    	if(rightJoystick.getFallingEdge(2)){
    		intake.in();
    	}
    	
    	//SHOOTING
    	if(rightJoystick.getRisingEdge(1)){
    		if(flywheel.getUpDown()){
    			drive.visionTurn();
    			visionTurn = true;
    			flywheel.setSpeed(2725);
    		}
    		else {
    			flywheel.setSpeed(2600);
    		}
    		shooting = true;
    	}
    	if (shooting && flywheel.atSpeed()) {
    		if (visionTurn) {
    			if (drive.facingGoal())
    			{
    				intake.shoot();
    	    		visionTurn = false;
    	    		shooting = false;
    			}
    		} else {
    			intake.shoot();
    			visionTurn = false;
    			shooting = false;
    		}
    	}
    	if ((Math.abs(leftJoystick.getRawAxis(1)) > 0.1 ||
    			Math.abs(rightJoystick.getRawAxis(0)) > 0.1) && visionTurn) {
    		visionTurn = false;
    		shooting = false;
    		flywheel.setSpeed(0);
    	}
    	if (Math.abs(leftJoystick.getRawAxis(1)) > 0.6 ||
    			Math.abs(rightJoystick.getRawAxis(0)) > 0.6) {
    		shooting = false;
    		flywheel.setSpeed(0);
    	}
    	
    }
    
    public void disabledInit() {
    	//recorder.stopRecording(); 
    	intake.up();
    	flywheel.setSpeed(0);
    	flywheel.down();
    	shooting = false;
    	visionTurn = false;
    }  
}
