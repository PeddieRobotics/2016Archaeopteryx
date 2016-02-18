package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;

public class Flywheel {

	private TalonSRX topMotor;
	private TalonSRX bottomMotor;
	private Solenoid mySolenoid;
	private PID myController;
	private Counter topCounter;
	private Counter bottomCounter;
	private double Kp;
	private double Ki;
	private double Kd;
	private double dV;
	private boolean upDown;
	/**
	 * Creates a new Flywheel
	 */
	public Flywheel() {
		topMotor = new TalonSRX(ElectricalLayout.FLYWHEEL_MOTOR);
		bottomMotor = new TalonSRX(ElectricalLayout.FLYWHEEL_MOTOR2);
		mySolenoid = new Solenoid(ElectricalLayout.FLYWHEEL_SOLENOID);
		myController = new PID(Kp, Ki, Kd, dV);
		
		topCounter = new Counter(ElectricalLayout.FLYWHEEL_COUNTER);
		topCounter.setDistancePerPulse(1);
		topCounter.setSamplesToAverage(2);
		bottomCounter = new Counter(ElectricalLayout.FLYWHEEL_COUNTER2);
		bottomCounter.setDistancePerPulse(1);
		bottomCounter.setSamplesToAverage(2);
		
	}
	
	/**
	 * Sets the flywheel's speed
	 * @param speed The desired speed of the flywheel, in rpm
	 */
	public void setSpeed(double speed) {
		myController.set(speed/60);
	}
	
	/**
	 * Returns the speed that the flywheel is moving at
	 * @return The speed of the flywhell, in rpm
	 */
	public double getSpeed() {
		return topCounter.getRate()*60;
	}
	
	/**
	 * Returns if the flywheel is at the desired speed
	 * @return True if the flywheel is within 50 rpm of the setpoint
	 */
	public boolean atSpeed(){
		if(Math.abs(topCounter.getRate()-myController.getSetpoint())<(50*60)){
			return true;
		} else
			return false;
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
		topMotor.set(myController.getOutput(topCounter.getRate()));
		bottomMotor.set(myController.getOutput(bottomCounter.getRate()));
		mySolenoid.set(upDown);
	}
}
