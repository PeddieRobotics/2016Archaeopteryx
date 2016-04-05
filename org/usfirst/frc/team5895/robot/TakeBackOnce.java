package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class TakeBackOnce {

	
	private double setpoint;
	private double h;
	private double lastTime;
	private double lastError;
	private double G;
	private double tSpeed;
	private boolean takenBack;
	
	/**
	 * Creates a new TakeBackHalf controller
	 * 
	 * @param gain The amount of motor output to increase per millisecond per error
	 */
	public TakeBackOnce(double gain, double topSpeed) {
		G = gain;
		tSpeed = topSpeed;
		lastError = 0;
		lastTime = 0;
		h = 1;
		setpoint = 0;
		takenBack = false;
	}
	
	/**
	 * Changes the target point to be setpoint
	 * 
	 * @param setpoint Where the mechanism should go to
	 */
	public void set(double setpoint) {
		
		if (setpoint < (this.setpoint - 100.0/60)) {
			h = 0;
		} else if (setpoint > (this.setpoint + 100.0/60)) {
			h = 1.0;
		}
		takenBack = false;
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
		lastTime = time;
		
		h += G*error*dt;
		
		if (!takenBack && (lastError*error) < 0) { //different signs
			h = setpoint/tSpeed;
			takenBack = true;
		//	DriverStation.reportError("once!", false);
		}
		
		if (h > 1) h = 1;
		else if (h < 0) h = 0;
		
		lastError = error;
		
		if (setpoint <= 10) {
			return 0;
		} else {
			return h;
		}	
	}
}
