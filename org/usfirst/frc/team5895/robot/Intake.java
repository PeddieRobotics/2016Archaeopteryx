package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
	
	TalonSRX intakeMotor;
	Solenoid upDownSolenoid;
	DigitalInput Sensor; 
	public boolean upDown;
	private double shootTimeStamp;
	
	public Intake(){
		intakeMotor = new TalonSRX(0);
		upDownSolenoid = new Solenoid(0);
		Sensor = new DigitalInput(0);
	}
	/**
	 * Moves the arm up
	 */
	public void up(){
		upDown = true;
	}
	/**
	 * Moves the arm down
	 */
	public void down(){
		upDown = false;
	}
	/**
	 * Shoots the ball
	 */
	public void shoot(){
		if (Sensor.get() == true){
			shootTimeStamp = Timer.getFPGATimestamp();
		}
	}
	
	public void update(){
		/**
		 * Gives the up or down value
		 */
		if ((Sensor.get() == false) || (Timer.getFPGATimestamp() < (shootTimeStamp+1))){
			intakeMotor.set(1);
		}
		else {
			intakeMotor.set(0);
		}
		if (upDown == true){
			upDownSolenoid.set(true);
		} else upDownSolenoid.set(false);
	}
	
	public boolean getUpDown(){
		return upDown;
	}
	/**
	 * Gets ball
	 * @return
	 */
	public boolean getBall(){
		return Sensor.get();
	}
	
}