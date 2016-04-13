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
	
	public double getRate() throws BadFlywheelException {
		double speed = c.getRate();
		
		if (speed > 20000/60.0) {
			throw new BadFlywheelException(lastSpeed);
		} else if (lastSpeed < 3600/60.0 && speed > 4600/60.0) {
			throw new BadFlywheelException(lastSpeed);
		} else {
			lastSpeed = speed;
			return speed;
		}
	}
	
	@SuppressWarnings("serial")
	public class BadFlywheelException extends Exception {
		private double lastSpeed;
		public BadFlywheelException(double lastSpeed) {
			this.lastSpeed = lastSpeed;
		}
		
		public double getLastSpeed() {
			return lastSpeed;
		}
	}
}
