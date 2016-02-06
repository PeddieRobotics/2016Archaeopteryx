package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class CDFArm {

	Solenoid myCylinder;
	
	boolean getPosition;
	
	public CDFArm(){
		myCylinder = new Solenoid(0);
	}
	
	public void up(){
		getPosition = true;
	}
	public void down(){
		getPosition = false;
	}
	
	public void update(){
		myCylinder.set(getPosition);
	}
	
	public boolean getArmPosition() {
	return getPosition;
			}
	
}
