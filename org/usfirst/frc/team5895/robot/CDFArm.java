package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class CDFArm {

	private Solenoid myCylinder;
	
	private boolean getPosition;
	
	public CDFArm() {
		myCylinder = new Solenoid(0);
	}
	
<<<<<<< HEAD
	public void up(){
		getPosition = true;
	}
	public void down(){
=======
	public void up() {
		myCylinder.set(true);
		getPosition = true;
	}
	public void down() {
		myCylinder.set(false);
>>>>>>> origin/master
		getPosition = false;
	}
	
	public void update(){
		myCylinder.set(getPosition);
	}
	
	public boolean getArmPosition() {
		return getPosition;
	}
	
}
