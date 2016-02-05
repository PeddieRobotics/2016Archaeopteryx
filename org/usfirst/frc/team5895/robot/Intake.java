package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

public class Intake {
	
	Spark myMotor;
	DigitalInput Sensor; 
	PID myController;
	Counter c;
	private double kP;
	private double kI;
	private double kD;
	private double dV;
	
	public Intake(){
		myMotor = new Spark(0);
		Sensor = new DigitalInput(0);
		myController = new PID(kP, kI, kD, dV);
		c = new Counter(8);
		c.setDistancePerPulse(1);
		c.setSamplesToAverage(2);
		
	}
	
	public void setSpeed(double speed){
		myController.set(speed/60);
	}
	
	public void update(){
		if (Sensor.equals(true)){
			myController.set(0);
		}
		myMotor.set(myController.getOutput(c.getRate()));
	}
	
}