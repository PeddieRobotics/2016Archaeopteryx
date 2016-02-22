package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Timer;

public class TakeBackHalf {

	private double setpoint;
	private double h;
	private double lastTime;
	private double lastError;
	private double G;
	private double h0;
	
	/**
	 * Creates a new TakeBackHalf controller
	 * 
	 * @param gain The amount of motor output to increase per millisecond per error
	 */
	public TakeBackHalf(double gain) {
		G = gain;
		h0 = 0;
		lastError = 0;
		lastTime = 0;
		h = 1;
		setpoint = 0;
	}
	
	/**
	 * Changes the target point to be setpoint
	 * 
	 * @param setpoint Where the mechanism should go to
	 * @param steady The approximate motor output to hold at constant speed
	 */
	public void set(double setpoint, double steady) {
		if (setpoint < this.setpoint) {
			h = 0;
			h0 = 0;
		} else {
			h0 = 2*steady - 1;
			h = 1;
		}
		
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
				
		double error = setpoint - proccessVar;
		
		double time = Timer.getFPGATimestamp() * 1000;
		double dt = time - lastTime;
		
		h += G*error*dt;
		
		if (h > 1) h = 1;
		else if (h < 0) h = 0;
		
		if ((lastError*error) < 0) { //different signs
			h = h0 = 0.5 * (h+h0);
		}
		
		lastError = error;
		
		if (setpoint == 0) {
			return 0;
		} else {
			return h;
		}
	}
}
