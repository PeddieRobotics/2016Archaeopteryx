package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.FlywheelCounter.BadFlywheelException;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Flywheel {

	private TalonSRX topMotor;
	private TalonSRX bottomMotor;
	private Solenoid mySolenoid;
	
	private FlywheelCounter topCounter;
	private FlywheelCounter bottomCounter;
	
	private TakeBackHalf topController;
	private TakeBackHalf bottomController;

	private boolean upDown;
	
	private int atSpeed;
	private double lastTime;
	
	/**
	 * Creates a new flywheel
	 */
	public Flywheel() {
		topMotor = new TalonSRX(ElectricalLayout.FLYWHEEL_TOPMOTOR);
		bottomMotor = new TalonSRX(ElectricalLayout.FLYWHEEL_BOTTOMMOTOR);
		mySolenoid = new Solenoid(ElectricalLayout.FLYWHEEL_SOLENOID);
		
		topController = new TakeBackHalf(0.00000002,6000/60,1.0/100);
		bottomController = new TakeBackHalf(0.00000002,6000/60,1.0/100);
		
		topCounter = new FlywheelCounter(ElectricalLayout.FLYWHEEL_TOPCOUNTER);
		bottomCounter = new FlywheelCounter(ElectricalLayout.FLYWHEEL_BOTTOMCOUNTER);
		
		atSpeed = 0;
		lastTime = Timer.getFPGATimestamp();
	}
	
	/**
	 * Sets the flywheel's speed
	 * @param speed The desired speed of the flywheel, in rpm
	 */
	public void setSpeed(double speed) {
		atSpeed = 0;
		bottomController.set(speed/60);
		topController.set(speed/60);
	}
	
	/**
	 * Sets the flywheel's speed
	 * @param speed The desired speed of the flywheel, in rpm
	 */
	public void setSpeed(double topSpeed, double bottomSpeed) {
		atSpeed = 0;
		bottomController.set(bottomSpeed/60);
		topController.set(topSpeed/60);
	}
	/**
	 * Returns the speed that the flywheel is moving at
	 * @return The speed of the flywheel, in rpm
	 */
	public double getSpeed() {
		try {
			return bottomCounter.getRate()*60;
		}
		catch (BadFlywheelException e){
			return e.getLastSpeed();
		}
	}
	
	/**
	 * Returns if the flywheel is at the desired speed
	 * @return True if the flywheel is within 25 rpm of the setpoint for the last 50ms
	 */
	public boolean atSpeed(){
		return atSpeed > 50;
	}
		
	public void up(){
		upDown = true;
	}
	
	public void down(){
		upDown = false;
	}
	
	public boolean getUpDown(){
		return upDown;
	}
	
	public void update() {
		double bottomOutput;
		double topOutput;
		double bottomSpeed;
		double topSpeed;
		try {
			bottomSpeed = bottomCounter.getRate();
			topSpeed = topCounter.getRate();
			bottomOutput = bottomController.getOutput(bottomSpeed);
			topOutput = topController.getOutput(topSpeed);
			bottomMotor.set(bottomOutput);
			topMotor.set(-1*topOutput);
			
			double dt = (Timer.getFPGATimestamp() - lastTime)*1000;
			if (Math.abs(bottomSpeed-bottomController.getSetpoint()) < 25.0/60 &&
					Math.abs(topSpeed-topController.getSetpoint()) < 25.0/60) {
				atSpeed += dt;
			} else {
				atSpeed = 0;
			}
		} catch (BadFlywheelException e) {
		}
		mySolenoid.set(upDown);
	}
}
