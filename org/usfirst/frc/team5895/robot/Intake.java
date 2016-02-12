package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
	
	private TalonSRX intakeMotor;
	private Solenoid upDownSolenoid;
	private DigitalInput sensor; 
	private boolean upDown;
	private double shootTimeStamp;
	
	public Intake() {
		intakeMotor = new TalonSRX(ElectricalLayout.INTAKE_MOTOR);
		upDownSolenoid = new Solenoid(ElectricalLayout.INTAKE_SOLENOID);
		sensor = new DigitalInput(ElectricalLayout.INTAKE_SENSOR);
		upDown = false;
		shootTimeStamp = Double.MIN_VALUE;
	}
	
	/**
	 * Moves the arm up
	 */
	public void up() {
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
	public void shoot() {
		if (sensor.get() == true) {
			shootTimeStamp = Timer.getFPGATimestamp();
		}
	}
	/**
	 * Returns whether or not the intake is up
	 * 
	 * @return True if the intake is down, false otherwise
	 */
	public boolean isDown() {
		return upDown;
	}
	
	/**
	 * Return whether or not the intake has a ball
	 * 
	 * @return True if there is a ball, false otherwise
	 */
	public boolean hasBall() {
		return sensor.get();
	}
	
	public void update() {
	
		if ((sensor.get() == false) || (Timer.getFPGATimestamp() < (shootTimeStamp+1))){
			intakeMotor.set(1);
		}
		else {
			intakeMotor.set(0);
		}
		
		upDownSolenoid.set(upDown);
	}

	
}