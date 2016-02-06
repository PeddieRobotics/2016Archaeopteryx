package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.framework.Waiter;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
	
	Spark intakeMotor;
	Solenoid upDownSolenoid;
	DigitalInput Sensor; 
	public boolean intaking;
	public boolean upDown;
	private double shootTimeStamp;
	
	public Intake(){
		intakeMotor = new Spark(0);
		upDownSolenoid = new Solenoid(0);
		Sensor = new DigitalInput(0);
	}
	
	public void up(){
		upDown = true;
	} 
	public void down(){
		upDown = false;
	}
	
	public void shoot(){
		if (Sensor.get() == true){
			shootTimeStamp = Timer.getFPGATimestamp();
		}
	}
	
	public void update(){
		if ((Sensor.get() == true)){
			intakeMotor.set(0);
			intaking = true;
		}
		if ((Sensor.get() == false) || (Timer.getFPGATimestamp() < (shootTimeStamp+1))){
			intakeMotor.set(1);
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