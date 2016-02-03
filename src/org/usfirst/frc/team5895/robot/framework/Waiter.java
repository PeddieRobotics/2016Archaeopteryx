package org.usfirst.frc.team5895.robot.framework;

import edu.wpi.first.wpilibj.Timer;


//To do: investigate better way to wait than Thread.sleep()
public class Waiter {
	
	/**
	 * Waits for the specified amount of time
	 * 
	 * @param time The amount of time to wait in milliseconds
	 */
	public static void waitFor(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			return;
		}
		return;
	}
	
	/**
	 * Waits for the specified method to return true, or for the specified time to elapse
	 * with a default precision of 10 milliseconds
	 * 
	 * @param condition The method to check
	 * @param time The amount of time to wait in milliseconds
	 */
	public static void waitFor(Checkable condition, double time) {
		waitFor(condition, time, 10);
	}
	
	/**
	 * Waits for the specified method to return true, or for the specified time to elapse
	 * 
	 * @param condition The method to check
	 * @param time The amount of time to wait in milliseconds
	 * @param precision The amount of time in milliseconds between checks to the condition method
	 */
	public static void waitFor(Checkable condition, double time, long precision) {
		double timeSeconds = time / 1000;
		double start = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp() - start < timeSeconds) {
			if (condition.check()) {
				return;
			}
			try {
				Thread.sleep(precision);
			} catch (InterruptedException e) {
				return;
			}
		}
		return;
	}
	
}
