package org.usfirst.frc.team5895.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Turret {

	Spark myMotor;
	PID myPID;
	Encoder e;
	private double myAngle;
	private double Kp;
	private double Ki;
	private double Kd;
	private double dV;
	private double degreesPerPulse;
	
	public Turret(){
		
		myMotor = new Spark(0);
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
	
	public void update(){
		
		myMotor.set(myPID.getOutput(e.getDistance()));
		
	}
}
