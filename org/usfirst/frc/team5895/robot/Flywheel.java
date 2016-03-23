package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.FlywheelCounter.BadFlywheelException;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Flywheel {

	private TalonSRX topMotor;
	private TalonSRX bottomMotor;
	private Solenoid mySolenoid;
	
	//private Counter topCounter;
	private FlywheelCounter bottomCounter;
	
	//private TakeBackHalf topController;
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
		
		//topController = new TakeBackHalf(0.00001);
		bottomController = new TakeBackHalf(0.00000002,6000/60,1.0/100);
		
		//topCounter = new Counter(ElectricalLayout.FLYWHEEL_TOPCOUNTER);
//		topCounter.setDistancePerPulse(1);
//		topCounter.setSamplesToAverage(2);
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
		bottomController.set(speed/60);
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
		double output;
		double speed;
		try {
			speed = bottomCounter.getRate();
			output = bottomController.getOutput(speed);
			bottomMotor.set(output);
			topMotor.set(-1*output);
			
			double dt = (Timer.getFPGATimestamp() - lastTime)*1000;
			if (Math.abs(speed-bottomController.getSetpoint()) < 25.0/60) {
				atSpeed += dt;
			} else {
				atSpeed = 0;
			}
		} catch (BadFlywheelException e) {
		}
		mySolenoid.set(upDown);
	}
}
