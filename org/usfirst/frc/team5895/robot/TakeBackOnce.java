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
	private boolean locked;
	private double lockTime;
	
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
		locked = false;
		lockTime = 0;
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
			takenBack = false;
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

	public void lock(){
		locked = true;
		lockTime = Timer.getFPGATimestamp()*1000;
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
		
		if (time - lockTime > 500){
			locked = false;
		}
		
		h += G*error*dt;
		
		if(error > 250.0/60 && !locked){
			h=1;
			takenBack = false;
			DriverStation.reportError("Error > 250", false);
		}
		
		if (error < -250.0/60){
			h=0;
			takenBack = false;
			DriverStation.reportError("Error < -250", false);
		}
		
		if (!takenBack && (error) < 0) { //different signs
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
