
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
    BetterJoystick operatorJoystick;
	
	Drive drive;
	CDFArm arm;
	Flywheel flywheel;
	Intake intake;
	Looper u;
	Looper r;
//	Looper v;
//	NewVision newVision;
	Vision vision;
	
	Recorder recorder;
	
	int matchCount;
	boolean visionTurn;
	boolean shooting;
	boolean primed;
	

//	TalonSRX turMotor;
	
	
    public void robotInit() {
    	
    	leftJoystick = new BetterJoystick(0);
        rightJoystick = new BetterJoystick(1);
        operatorJoystick = new BetterJoystick(2);
    	
    	u = new Looper(10);
    	r = new Looper(250);
//    	v = new Looper(50);
   	
    	vision = new Vision();
//    	newVision = new NewVision();
    	drive = new Drive(vision);
//		drive = new Drive(newVision);
//    	arm = new CDFArm();
    	flywheel = new Flywheel();
    	intake = new Intake();
    	
     	recorder = new Recorder(drive,arm,flywheel,intake);
    	matchCount = recorder.incrementCount();
        
        visionTurn = false;
        shooting = false;
        primed = false;
     	
//        v.add(newVision::update);
    	u.add(intake::update);
    	u.add(drive::update);
    	u.add(flywheel::update);
//    	u.add(turret::update);
    	
//    	turMotor = new TalonSRX(ElectricalLayout.TURRET_MOTOR);
     	
    	u.start();
    	r.start();
//    	v.start();
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
    	
    	boolean shoot = true;
    	
    	if (defense.contains("rough")) {
    		drive.driveVoltage(0.6, angle);
        	Waiter.waitFor(2400);
        	drive.haloDrive(0, 0);
    	} else if (defense.contains("rock")) {
    		drive.driveVoltage(0.6, angle);
        	Waiter.waitFor(2600);
        	drive.haloDrive(0, 0);
    	} else if (defense.contains("ramp")) {
    		drive.driveVoltage(0.7, angle);
    		Waiter.waitFor(2300);
    		drive.haloDrive(0, 0);
    	} else if (defense.contains("moat")){
    		intake.down();
    		drive.driveVoltage(1.00, angle);
    		Waiter.waitFor(4000);
    	} else if (defense.contains("reach")) {
    		drive.driveVoltage(0.4, angle);
    		Waiter.waitFor(2000);
    		drive.haloDrive(0, 0);
    		shoot = false;
    	} else if (defense.contains("nope")){
    		drive.haloDrive(0, 0);
    		shoot = false;
    	}
    	
    	if(position.contains("alt5")){
    		Waiter.waitFor(200);
    		flywheel.setSpeed(2700);
    		
    		drive.turnTo(angle-45);
    		Waiter.waitFor(2000);
    		
    		drive.driveVoltage(0.37, angle-20);
    		Waiter.waitFor(1000);
    		
    		drive.turnTo(angle-10);
    		Waiter.waitFor(1500);
    		drive.haloDrive(0, 0);
    	}
    	else if(position.contains("alt2")){
    		Waiter.waitFor(200);
    		flywheel.setSpeed(2700);
    		
    		drive.turnTo(angle+85);
    		Waiter.waitFor(2000);
    		drive.haloDrive(0,0);
    		
    		drive.driveVoltage(0.37, angle+85);
    		Waiter.waitFor(1000);
    		drive.haloDrive(0, 0);
    		
    		Waiter.waitFor(100);
    		drive.turnTo(angle);
    		Waiter.waitFor(2000);
    		drive.haloDrive(0,0);
    	}
    	else if (position.contains("2")) {
    		
    		Waiter.waitFor(200);
    		flywheel.setSpeed(2700);
    		
    		drive.turnTo(angle);
    		Waiter.waitFor(2000);
    		drive.haloDrive(0,0);
    	
    		drive.driveVoltage(0.37, angle);
    		Waiter.waitFor(1600); //driving forward *********
    		drive.haloDrive(0, 0);
    		
    		Waiter.waitFor(100);
    		drive.turnTo(angle+35); //turn to goal (not Vision Turn) ****
    		Waiter.waitFor(2200);
    		drive.haloDrive(0,0);
    		
    		/*Waiter.waitFor(100);
    		drive.driveVoltage(-0.3, angle+35);
    		Waiter.waitFor(1000);
    		drive.haloDrive(0,0);
    		*/
    	
    	} else if (position.contains("3")) {
    		
    		Waiter.waitFor(200);
    		flywheel.setSpeed(2710);
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
    	
    		drive.driveVoltage(0.4, angle);
    		Waiter.waitFor(1700); //driving forward *****
    		drive.haloDrive(0, 0);
    		
    		Waiter.waitFor(100);
    		drive.turnTo(angle-35); //turn to goal (not Vision Turn) ****
    		Waiter.waitFor(3000);
    		drive.haloDrive(0,0);
    		
    		/*Waiter.waitFor(100);
    		drive.driveVoltage(-0.3, angle-35);
    		Waiter.waitFor(500);
    		drive.haloDrive(0,0);
    		*/
    	
    	}
    	
    	if (shoot) {
    		drive.visionTurn();
    		Waiter.waitFor(3000);
    		Waiter.waitFor(flywheel::atSpeed, 1200);
    		drive.haloDrive(0, 0);
    		if (vision.hasTarget()) {
    			flywheel.lock();
    			intake.shoot();
    		}
    	}
    	
    }

    public void teleopInit() {
 	  // recorder.startRecording("teleop"+matchCount+".csv");
    }
    
    public void teleopPeriodic() {

    	if (rightJoystick.getRisingEdge(3)) {
    		flywheel.setSpeed(0);
    		flywheel.down();
    		shooting = false;
    		visionTurn = false;
    		primed = false;
    	} else if (rightJoystick.getRisingEdge(4)) {
    		flywheel.setSpeed(0);
    		flywheel.up();
    		shooting = false;
    		visionTurn = false;
    		primed = false;
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
    	if(rightJoystick.getRisingEdge(1) && !shooting){
    		if(flywheel.getUpDown()){
    			drive.visionTurn();
    			visionTurn = true;
    		//	flywheel.setSpeed(2700+SmartDashboard.getNumber("DB/Slider 2"));
    			flywheel.setSpeed(3550 + SmartDashboard.getNumber("DB/Slider 1"), 2100 + SmartDashboard.getNumber("DB/Slider 1"));
    		}
    		else {
    		//	flywheel.setSpeed(2600+SmartDashboard.getNumber("DB/Slider 2"));
    			flywheel.setSpeed(2600 + SmartDashboard.getNumber("DB/Slider 2"), 2600 + SmartDashboard.getNumber("DB/Slider 3"));
    		}
    		shooting = true;
    		primed = false;
    	}
    	if (shooting && visionTurn && flywheel.atSpeed()) {
    		if (drive.facingGoal())
    		{
    			flywheel.lock();
    			intake.shoot();
    	   		visionTurn = false;
    	   		shooting = false;	
    		}
    	}
    	if (shooting && !visionTurn && flywheel.atSpeedLoose()) {
    			flywheel.lock();
    			intake.shoot();
    			shooting = false;
    	}
    	if (((Math.abs(leftJoystick.getRawAxis(1)) > 0.1 ||
    			Math.abs(rightJoystick.getRawAxis(0)) > 0.1)) && visionTurn && !primed) {
    		visionTurn = false;
    		shooting = false;
    		flywheel.setSpeed(0);
    	}
    	if ((Math.abs(leftJoystick.getRawAxis(1)) > 0.5 ||
    			Math.abs(rightJoystick.getRawAxis(0)) > 0.5) && !primed) {
    		shooting = false;
    		flywheel.setSpeed(0);
    	}
    	
    	if (operatorJoystick.getRisingEdge(1)){
    			flywheel.override(0.45); //OVERRIDE VOLTAGE **********
    			shooting = false;
    			visionTurn = false;
    		
    	}
    	if(operatorJoystick.getRisingEdge(2)){
    		intake.in();
    	}
    	if(operatorJoystick.getRisingEdge(3)){
    		intake.out();
    	}
    	if(operatorJoystick.getFallingEdge(3)){
    		intake.in();
    	}
//    	if(operatorJoystick.getRisingEdge(4)){
//    		vision.reset();
//    	}
    	if(operatorJoystick.getRisingEdge(5)){
    		if(flywheel.getUpDown()){
    			flywheel.setSpeed(3550 + SmartDashboard.getNumber("DB/Slider 1"), 2100 + SmartDashboard.getNumber("DB/Slider 1"));
    		} else {
    			flywheel.setSpeed(2600+SmartDashboard.getNumber("DB/Slider 2"), 2600 + SmartDashboard.getNumber("DB/Slider 3"));
    		}
    		primed = true;
    	}
//    	if(operatorJoystick.getRisingEdge(6)){
//    		newVision.cam0;
//    	}
//    	if(operatorJoystick.getRisingEdge(7)){
//    		newVision.cam1;
//    	}
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
