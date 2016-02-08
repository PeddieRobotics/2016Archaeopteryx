package org.usfirst.frc.team5895.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;

public class Turret {

	TalonSRX myMotor;
	PID myPID;
	Encoder e;
	private double Kp;
	private double Ki;
	private double Kd;
	private double dV;
	private double degreesPerPulse;
	
	public Turret(){
		
		myMotor = new TalonSRX(0);
		myPID = new PID(Kp, Ki, Kd, dV);
		e = new Encoder(0,1);
		e.setDistancePerPulse(degreesPerPulse);
	}
	
	public void set(double angle){
		myPID.set(angle);
	}
		
	public double getAngle(){
		return e.getDistance();
	}
	
	public boolean atAngle(){
		if(Math.abs(e.getDistance()-myPID.getSetpoint())<0.25){
			return true;
	}else return false;
	}
	public void update(){
		
		myMotor.set(myPID.getOutput(e.getDistance()));
		
	}
}
