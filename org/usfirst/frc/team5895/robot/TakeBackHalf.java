package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Timer;

public class TakeBackHalf {

	private double setpoint;
	private double h_last;
	private double lastTime;
	private double lastError;
	private double G;
	private double h0;
	
	public TakeBackHalf(double gain) {
		G = gain;
		h0 = 0;
		lastError = 0;
		lastTime = 0;
		h_last = 0;
		setpoint = 0;
	}
	
	/**
	 * Changes the target point to be setpoint
	 * 
	 * @param setpoint Where the mechanism should go to
	 */
	public void set(double setpoint) {
		this.setpoint = setpoint;
	}
	
	/**
	 * Returns the target position
	 * 
	 * @return The setpoint
	 */
	public double getSetpoint() {
		return setpoint;
	}

	/**
	 * Returns the output for the mechanism (should be called periodically)
	 * 
	 * @param proccessVar The current location of the mechanism
	 * @return The output to the motor controlling the mechanism
	 */
	public double getOutput(double proccessVar) {
		if (setpoint == 0) {
			h_last = 0;
			lastTime = Timer.getFPGATimestamp() * 1000;
			return 0;
		}
				
		double error = setpoint - proccessVar;
		
		double time = Timer.getFPGATimestamp() * 1000;
		double dt = time - lastTime;
		
		h_last += G*error*dt;
		
		if (h_last > 1) h_last = 1;
		else if (h_last < 0) h_last = 0;
		
		if ((lastError*error) < 0) { //different signs
			h_last = h0 = 0.5 * (h_last+h0);
		}
		
		
		lastError = error;
		return h_last;
	}
}
