package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Timer;

public class PID{
	
	private final boolean RESET_ZERO_CROSS;

	private final double Kp;
	private final double Ki;
	private final double Kd;
	private final double dV;
	
	private double setpoint;
	private double lastTime = 0;
	private double errorSum = 0;
	private double lastError = 0;
	private double lastSetpoint = 0;
	private double lastOutput = 0;
	private boolean saturated = false;
	
	/**
	 * Initializes a new PID controller
	 * 
	 * @param Kp The proportional gain
	 * @param Ki The integral gain
	 * @param Kd The derivative gain
	 * @param dV The maximum amount the voltage can change per ms
	 * @param reset_zero_cross If true, resets the integral term whenever the error crosses zero
	 */
	public PID(double Kp, double Ki, double Kd, double dV, boolean reset_zero_cross) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.dV = dV;
		RESET_ZERO_CROSS = reset_zero_cross;
		setpoint = 0;
		lastTime = 0;
		errorSum = 0;
		lastError = 0;
		lastSetpoint = 0;
		lastOutput = 0;
	}
	
	/**
	 * Initializes a new PID controller
	 * 
	 * @param Kp The proportional gain
	 * @param Ki The integral gain
	 * @param Kd The derivative gain
	 * @param dV The maximum amount the voltage can change per ms
	 */
	public PID(double Kp, double Ki, double Kd, double dV) {
		this(Kp, Ki, Kd, dV, true);
	}
	
	/**
	 * Resets the integral term
	 */
	public void resetIntegral() {
		errorSum = 0;
	}

	/**
	 * Changes the target point to be setpoint
	 * 
	 * @param setpoint Where the mechanism controlled my the PID should go to
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

		double error = setpoint - proccessVar;
		
		//did the setpoint change?
		if (setpoint != lastSetpoint) {
			errorSum = 0;
			lastError = error;
		}
		else if (RESET_ZERO_CROSS && (lastError * error < 0)) {
			errorSum = 0;  //reset if lastError and error have different signs
		}
		
		double time = Timer.getFPGATimestamp() * 1000;
		double dt = time - lastTime;
		
/*		if (!saturated) {  
			errorSum += error * dt;   //only integrate error if output isn't saturated 
		}
*/
		errorSum += error * dt; //always integrate error
		
		double dError = (error - lastError) / dt;  //should this be smoothed over
		                                           //multiple measurements?
		
		double output = Kp * error + Ki * errorSum + Kd * dError;
		
		saturated = false;
		//limit the amount of voltage the output can change per ms
		if (output - lastOutput > (dt * dV)) {
			output = lastOutput + (dt * dV);
			saturated = true;
		}
		if (output - lastOutput < -(dt * dV)) {
			output = lastOutput - (dt * dV);
			saturated = true;
		}
		if (output >= 1.0 || output <= -1.0) {
			saturated = true;
		}
		
		//set variables for next run through loop
		lastTime = time;
		lastSetpoint = setpoint;
		lastError = error;
		lastOutput = output;
		
		return output;
	}

}
