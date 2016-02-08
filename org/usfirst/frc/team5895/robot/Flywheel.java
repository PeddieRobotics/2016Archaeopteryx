package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.TalonSRX;

public class Flywheel {

	private TalonSRX myMotor;
	private PID myController;
	private Counter c;
	private double Kp;
	private double Ki;
	private double Kd;
	private double dV;
	
	/**
	 * Creates a new Flywheel
	 */
	public Flywheel() {
		myMotor = new TalonSRX(0);
		myController = new PID(Kp, Ki, Kd, dV);
		
		c = new Counter(8);
		c.setDistancePerPulse(1);
		c.setSamplesToAverage(2);
		
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
		return c.getRate()*60;
	}
	
	/**
	 * Returns if the flywheel is at the desired speed
	 * @return True if the flywheel is within 50 rpm of the setpoint
	 */
	public boolean atSpeed(){
		if(Math.abs(c.getRate()-myController.getSetpoint())<(50*60)) {
			return true;
		} else
			return false;
	}
		
	public void update() {
		myMotor.set(myController.getOutput(c.getRate()));
	}
}
