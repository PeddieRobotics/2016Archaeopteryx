package org.usfirst.frc.team5895.robot;

public class BangBang{
	private double setpoint;
	
	
	public void set(double s){
		setpoint=s;
	}
	
	public double getSetpoint() {
		return setpoint;
	}
	
	public double getOutput(double processVar) {
		double error= setpoint-processVar;
		if (error <= 0){
			return 0;
		}
		else {
			return 1;
		}
	}
	
}
