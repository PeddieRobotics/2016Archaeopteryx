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
	public void down(){
=======
	
	public void up() {
>>>>>>> origin/master
		upDown = false;
	} 
	public void down() {
		upDown = true;
	}
<<<<<<< HEAD
	/**
	 * Shoots the ball
	 */
	public void shoot(){
=======
	
	public void shoot() {
>>>>>>> origin/master
		if (Sensor.get() == true){
			shootTimeStamp = Timer.getFPGATimestamp();
		}
	}
	
	public boolean getUpDown() {
		return upDown;
	}
	
	public boolean getBall() {
		return Sensor.get();
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
<<<<<<< HEAD
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
=======
		} else {
			upDownSolenoid.set(false);
		}
>>>>>>> origin/master
	}
	
}