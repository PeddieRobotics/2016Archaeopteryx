package org.usfirst.frc.team5895.robot;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Scanner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Drive {
	
	private boolean recordFile;

	//for turn()
	private static final double TURN_KP = 0.02;
	private static final double TURN_KI = 0.00000001;
	
	//for driveStraight()
	private static final double DRIVE_KP = 0.04;
	private static final double DRIVE_KI = 0.0000001;
	private static final double DRIVE_TURN_KP = 0.1;
	private static final double DRIVE_TURN_KI = 0;
	
	private enum Mode_Type {TELEOP, AUTO_TURN, AUTO_STRAIGHT};
	
	private Mode_Type mode = Mode_Type.TELEOP;
	private PID turnPID;
	private PID straightPID;
	private PID straightTurnPID;
	
	private Talon rightMotor;
    private Talon leftMotor;
    private Formatter f;
    private Scanner sca;

    private double rightMotorSpeed = 0;
    private double leftMotorSpeed = 0;

    private NavX ahrs;
    
    private Encoder enc;
    
    public Drive()
    {
    	rightMotor = new Talon(2);
    	leftMotor = new Talon(1);
    	ahrs = new NavX();
    	turnPID = new PID(TURN_KP, TURN_KI , 0, 0.05);
    	straightPID = new PID(DRIVE_KP, DRIVE_KI, 0, 0.05);
    	straightTurnPID= new PID(DRIVE_TURN_KP, DRIVE_TURN_KI, 0, 0.05);
    	
    	enc= new Encoder(0, 1, false, EncodingType.k4X);
    	enc.setDistancePerPulse((4*Math.PI)/360);
    	
    	enc.reset();
    	ahrs.reset();
    }
    
    public void startRecording(String filename) {
    	try {
    		if (recordFile==false){
    		f= new Formatter("/c/Logs/Drive//" + filename);
    		f.format("Time,Angle,Distance");
    		recordFile=true;
    		}
    	} catch (FileNotFoundException e) {
    		DriverStation.reportError(
    				"File not Found Exception in Drive::startRecording\n", false);
	 }
    }
    
    public void stopRecording(){
    	try {
    		if (recordFile==true){
    			f.close();
    			recordFile=false;
    		}
    	} catch (FormatterClosedException e) {
    		DriverStation.reportError(
    				"Formatter Closed Exception in Drive::stopRecording\n", false);
    	}
    }
    
    public void record() {
    	if (recordFile==true){
    		f.format("\r\n%f,%f,%f", Timer.getFPGATimestamp(), getAngle(), enc.getDistance());
    	}
    }
    
    /**
     * Gets the angle the of the robot
     * 
     * @return Returns the angle the robot is facing in degrees
     */
    public double getAngle(){
    	return ahrs.getAngle();
    }
    
    public void haloDrive(double throttle, double turn) {
        rightMotorSpeed = -1*(throttle+turn);
        leftMotorSpeed = throttle-turn;
        mode = Mode_Type.TELEOP;
    }

    /**
     * Turns the robot to the specified angle
     * 
     * @param angle The angle the robot should turn to in degrees
     */
    public void turnTo(double angle)
    {
    	mode = Mode_Type.AUTO_TURN;
    	turnPID.set(angle);
    }
    
    /**
     * Returns true if the robot is at the angle last specified by
     * turnTo()
     * 
     * @return True if the robot is within 2 degrees of the desired angle
     */
    public boolean atAngle(){
    	if((Math.abs(turnPID.getSetpoint()-ahrs.getAngle())<=2)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    /**
     * Drives the robot the specified distance from the current location
     * And keeps the robot on the specified angle
     * 
     * @param distance The relative distance to drive in inches
     * @param angle The absolute angle to face in degrees
     */
    public void driveStraight(double distance, double angle)
    {
    	mode = Mode_Type.AUTO_STRAIGHT;
    	enc.reset();
    	straightPID.set(distance);
    	straightTurnPID.set(angle);
    
    }
    
    
    /**
     * Returns true if the robot has driven the distance last specified by driveStaight
     * 
     * @return True if the robot is within 2 inches of the desired distance
     */
    public boolean atDistance()
    {
    	if((Math.abs(straightPID.getSetpoint()-enc.getDistance())<=2)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public void stopDrive() {
        rightMotorSpeed = 0.0;
        leftMotorSpeed = 0.0;
    }
    
    public void update() {
    		switch (mode)
    	{
    	case TELEOP:
            rightMotor.set(rightMotorSpeed);
            leftMotor.set(leftMotorSpeed);  
//    		DriverStation.reportError("the angle is " + getAngle() + "\n", false);
    		break;
    		
    	case AUTO_TURN:
    		double speed=turnPID.getOutput(ahrs.getAngle());
//    		DriverStation.reportError("the output is " + speed + "\n", false);
    		
//    		// this is code for testing, remove it later
    		if(speed>0.8) {
    			speed = 0.8;
    		}
    		if(speed<-0.8){
    			speed = -0.8;
    		}
//    		
//    		// end tfesting code
    		
//    		if (atAngle()==true){
//    			speed=0;
//    		}
    		DriverStation.reportError("the angle is " + getAngle() + "\n", false);
    		DriverStation.reportError("the output is " + speed + "\n", false);
    		leftMotor.set(-speed);
    		rightMotor.set(-speed);
    		break;
    		
    	case AUTO_STRAIGHT:
    		double sped = straightPID.getOutput(enc.getDistance());
    		
    		if(sped>0.5) {
    			sped = 0.5;
    		}
    		if(sped<-0.5){
    			sped = -0.5;
    		}
    		DriverStation.reportError("the angle is " + getAngle() + "\n", false);
    		DriverStation.reportError("The Distance is " + enc.getDistance() +" inches \n", false);
    		
    		leftMotor.set(-sped);
    		rightMotor.set(sped);
    		break;
    	}
     
    }

}