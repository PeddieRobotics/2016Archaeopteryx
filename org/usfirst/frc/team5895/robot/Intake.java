package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

public class Intake {
	
	Spark intakeMotor;
	Solenoid upDownSolenoid;
	DigitalInput Sensor; 
	public boolean intaking;
	public boolean upDown;
	
	public Intake(){
		intakeMotor = new Spark(0);
		upDownSolenoid = new Solenoid(0);
		Sensor = new DigitalInput(0);
	}
	
	public void on(){
		intakeMotor.set(1);
		intaking = true;
	}
	
	public void up(){
		upDown = true;
	} 
	public void down(){
		upDown = false;
	}
	
	public void shoot(){
		if (Sensor.get()== true){
			intakeMotor.set(1);
		}
	}
	
	public void update(){
		if (Sensor.equals(true)){
			intakeMotor.set(0);
			intaking = true;
		}
		if (upDown == true){
			upDownSolenoid.set(true);
		} else upDownSolenoid.set(false);
	}
	
	public boolean getUpDown(){
		return upDown;
	}
	
	public boolean getIntaking(){
		return intaking;
	}
	
	public boolean getBall(){
		return Sensor.get();
	}
	
}