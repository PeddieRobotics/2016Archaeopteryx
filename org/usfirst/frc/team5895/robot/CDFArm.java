package org.usfirst.frc.team5895.robot;

import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class CDFArm {

	Solenoid myCylinder;
	
	boolean getPosition;
	
	public CDFArm(){
		myCylinder = new Solenoid(0);
	}
	
	public void up(){
		myCylinder.set(true);
		getPosition = true;
	}
	public void down(){
		myCylinder.set(false);
		getPosition = false;
	}
	
	public boolean getArmPosition() {
		if(getPosition ==false){
		return false;
		}
		else{
		return true;
		}
	}
	
}
