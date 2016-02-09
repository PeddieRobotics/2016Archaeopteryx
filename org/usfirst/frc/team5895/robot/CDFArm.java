package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class CDFArm {

	private Solenoid myCylinder;
	private boolean getPosition;
	
	/**
	 * Constructs a new CDFArm object
	 */
	public CDFArm() {
		myCylinder = new Solenoid(ElectricalLayout.ARM_SOLENOID);
		getPosition = false;
	}
	
	/**
	 * Sets the arm to the up position
	 */
	public void up(){
		getPosition = false;
	}
	
	/**
	 * Sets the arm to the down position
	 */
	public void down() {
		getPosition = true;
	}
	
	/**
	 * Returns if the arm is up or down
	 * 
	 * @return True if the arm is down, false if it's up
	 */
	public boolean getArmPosition() {
		return getPosition;
	}
	
	public void update(){
		myCylinder.set(getPosition);
	}

	
}
