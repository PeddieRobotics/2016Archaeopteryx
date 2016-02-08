package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
	
	private TalonSRX intakeMotor;
	private Solenoid upDownSolenoid;
	private DigitalInput Sensor; 
	private boolean upDown;
	private double shootTimeStamp;
	
	public Intake(){
		intakeMotor = new TalonSRX(0);
		upDownSolenoid = new Solenoid(0);
		Sensor = new DigitalInput(0);
		upDown = false;
		shootTimeStamp = Double.MIN_VALUE;
	}
<<<<<<< HEAD
	/**
	 * Moves the arm up
	 */
	public void up(){
		upDown = true;
	}
	/**
	 * Moves the arm down
	 */
	public void down() {
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
	/**
	 * Gives the up or down value
	 */
	public boolean getUpDown() {
		return upDown;
	}
	/**
	 *gives whether it has a ball
	 */
	public boolean getBall() {
		return Sensor.get();
	}
	
	public void update(){
	
		if ((Sensor.get() == false) || (Timer.getFPGATimestamp() < (shootTimeStamp+1))){
			intakeMotor.set(1);
		}
		else {
			intakeMotor.set(0);
		}
			upDownSolenoid.set(upDown);
	}
	
}