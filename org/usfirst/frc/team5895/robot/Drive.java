package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

	//for turn()
	private static final double TURN_KP = 0.004;
	private static final double TURN_KI = 0.00005;
	
	//for visionTurn()
	private static final double VISION_TURN_KP = 1.9;
	private static final double VISION_TURN_KI = 0.0009;
	
	//for driveStraight()
	private static final double DRIVE_KP = 0.04;
	private static final double DRIVE_KI = 0.0000001;
	
	private static final double DRIVE_TURN_KP = 0.02;
	private static final double DRIVE_TURN_KI = 0;
	
	private enum Mode_Type {TELEOP, AUTO_TURN, VISION_TURN, AUTO_STRAIGHT_DISTANCE, AUTO_STRAIGHT_VOLTAGE};
	
	private Mode_Type mode = Mode_Type.TELEOP;
	private PID turnPID;
	private PID visionTurnPID;
	private PID straightPID;
	private PID straightTurnPID;
	
	private TalonSRX rightMotor;
    private TalonSRX leftMotor;
    
    private double rightMotorSpeed = 0;
    private double leftMotorSpeed = 0;
    private double spd = 0;
    
    private double lastTime = Timer.getFPGATimestamp()*1000;
    private double facingGoal = 0;
    private boolean facedGoal = false;

    private NavX ahrs;
    private DriveEncoder enc;
    private Vision v;
    
    public Drive(Vision v)
    {
    	rightMotor = new TalonSRX(ElectricalLayout.DRIVE_RIGHTMOTOR);
    	leftMotor = new TalonSRX(ElectricalLayout.DRIVE_LEFTMOTOR);
    	ahrs = new NavX();
    	turnPID = new PID(TURN_KP, TURN_KI , 0, 0.05, 0.35);
    	visionTurnPID = new PID (VISION_TURN_KP, VISION_TURN_KI, 0, 0.05);
    	straightPID = new PID(DRIVE_KP, DRIVE_KI, 0, 0.05);
    	straightTurnPID= new PID(DRIVE_TURN_KP, DRIVE_TURN_KI, 0, 0.05);
    	
    	enc= new DriveEncoder();
    	
    	enc.reset();
    	ahrs.reset();
    	
    	this.v = v;
    }
    
    /**
     * Returns the angle the of the robot
     * 
     * @return The angle the robot is facing in degrees
     */
    public double getAngle(){
    	return ahrs.getAngle();
    }
    
    /**
     * Returns the distance the robot has traveled
     * @return The distance the robot has traveled, in inches
     */
    public double getDistance(){
    	return enc.getDistance();
    }
    
    public void haloDrive(double throttle, double turn) {
        rightMotorSpeed = (throttle+turn);
        leftMotorSpeed = -1*(throttle-turn);
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
     * turns to face goal
     */
    public void visionTurn() {
    	mode = Mode_Type.VISION_TURN;
    	facingGoal = 0;
    	facedGoal = false;
    	visionTurnPID.resetIntegral();
    	//left higher, right lower ********
    	visionTurnPID.set(0.05); //IF DOESNT TURN PROPERLY ***********
    }
    
    /**
     * turns to face goal
     * 
     * @param Where to aim to (from -1 to 1)
     */
    public void visionTurn(double setpoint) {
    	mode = Mode_Type.VISION_TURN;
    	facingGoal = 0;
    	facedGoal = false;
    	visionTurnPID.resetIntegral();
    	visionTurnPID.set(setpoint);
    }
    
    public boolean facingGoal() {
    	return (facingGoal > 75) || facedGoal;
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
    	mode = Mode_Type.AUTO_STRAIGHT_DISTANCE;
    	enc.reset();
    	straightPID.set(distance);
    	straightTurnPID.set(angle);
    
    }
    
    /**
     * Drives the robot at a constant voltage and keeps the same angle
     * 
     * @param voltage The voltage for the wheels to be kept at
     * @param angle The absolute angle to face in degrees
     */
    public void driveVoltage (double voltage, double angle) {
    	mode = Mode_Type.AUTO_STRAIGHT_VOLTAGE;
    	straightTurnPID.set(angle);
    	spd = voltage;
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

    		if(speed>0.3) {
    			speed = 0.3;
    		}
    		if(speed<-0.3){
    			speed = -0.3;
    		}

    		DriverStation.reportError("the angle is " + (getAngle()-turnPID.getSetpoint()) + "\n", false);
    		leftMotor.set(speed);
    		rightMotor.set(speed);
    		break;
    		
    	case VISION_TURN:
    		double speeed = visionTurnPID.getOutput(v.getX());
    		if(speeed>0.27) {
    			speeed = 0.27;
    		}
    		if(speeed<-0.27) {
    			speeed = -0.27;
    		}
    		
    		if (facingGoal()) {
    			facedGoal = true;
    		}
    		
    		if (facedGoal) {
    			speeed = 0;
    		}
    		
    		DriverStation.reportError(SmartDashboard.getNumber("DB/Slider 0", 0) + "\n", false);

    		leftMotor.set(-speeed);
    		rightMotor.set(-speeed);
    		break;
    		
    	case AUTO_STRAIGHT_DISTANCE:
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
    		
    	case AUTO_STRAIGHT_VOLTAGE:
    		double turn = straightTurnPID.getOutput(ahrs.getAngle());
    		
    		if (turn > 0.3) {
    			turn = 0.3;
    		} else if (turn < -0.3) {
    			turn = -0.3;
    		}
    		
    		rightMotor.set(-1*(spd - turn));
    		leftMotor.set(spd+turn);
    		break;
    	}
     
    	double time = Timer.getFPGATimestamp() * 1000;
		double dt = time - lastTime;
		lastTime = time;
		if (v.hasTarget() && Math.abs(v.getX()-visionTurnPID.getSetpoint()) < 0.016) {
			facingGoal += dt;
		} else {
			facingGoal = 0;
		}
    	
    }

}