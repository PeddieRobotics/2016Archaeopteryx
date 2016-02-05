package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class CDFArm {

	Solenoid myCylinder;
	
	public CDFArm(){
		myCylinder = new Solenoid(0);
	}
	
	public void up(){
		myCylinder.set(true);
	}
	public void down(){
		myCylinder.set(false);
	}
}
