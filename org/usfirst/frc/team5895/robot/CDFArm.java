package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class CDFArm {

	private Solenoid myCylinder;
	
	private boolean getPosition;
	
	public CDFArm() {
		myCylinder = new Solenoid(0);
	}
	
	public void up() {
		myCylinder.set(true);
		getPosition = true;
	}
	public void down() {
		myCylinder.set(false);
		getPosition = false;
	}
	
	public boolean getArmPosition() {
		return getPosition;
	}
	
}
