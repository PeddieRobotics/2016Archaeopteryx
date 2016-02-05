package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

public class Intake {
	
	Spark myMotor;
	DigitalInput Sensor; 
	
	public Intake(){
		myMotor = new Spark(0);
		Sensor = new DigitalInput(0);
		
	}
	
	public void on(){
		myMotor.set(1);
	}
	
	public void shoot(){
		if (Sensor.get()== true){
			myMotor.set(1);
		}
	}
	
	public void update(){
		if (Sensor.equals(true)){
			myMotor.set(0);
		}
	}
	
}