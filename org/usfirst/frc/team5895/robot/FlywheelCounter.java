package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;

public class FlywheelCounter {

	private Counter c;
	private double lastSpeed;
	
	public FlywheelCounter(int port) {
		c = new Counter(port);
		c.setDistancePerPulse(1);
		
		lastSpeed = 0;
	}
	
	public double getRate() {
		double speed = c.getRate();
		
		if (speed > 1000 && speed > 1.5*lastSpeed) {
			return lastSpeed;
		} else {
			lastSpeed = speed;
			return speed;
		}
	}
}
