package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.TalonSRX;

public class Flywheel {

	TalonSRX myMotor;
	PID myController;
	Counter c;
	private double Kp;
	private double Ki;
	private double Kd;
	private double dV;
	/**
	 * Creates a new Flywheel
	 */
	public Flywheel(){
		myMotor = new TalonSRX(0);
		myController = new PID(Kp, Ki, Kd, dV);
		
		c = new Counter(8);
		c.setDistancePerPulse(1);
		c.setSamplesToAverage(2);
		
	}
	/**
	 *Sets the speed at a certain speed
	 * @param speed
	 */
	public void setSpeed(double speed){
		myController.set(speed/60);
	}
	/**
	 * Gets the speed that the Flywheel is moving at
	 * @return
	 */
	public double getSpeed() {
		return c.getRate()*60;
	}
	public boolean atSpeed(){
		if(Math.abs(c.getRate()-myController.getSetpoint())<50){
			return true;
		}else return false;
	}
		
	/**
	 * Updates the reading at points throughout
	 */
	public void update() {
		myMotor.set(myController.getOutput(c.getRate()));
	}
}
