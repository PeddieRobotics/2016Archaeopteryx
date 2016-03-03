package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.FlywheelCounter.BadFlywheelException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;

public class Flywheel {

	private TalonSRX topMotor;
	private TalonSRX bottomMotor;
	private Solenoid mySolenoid;
	
	//private Counter topCounter;
	private FlywheelCounter bottomCounter;
	
	//private TakeBackHalf topController;
	private TakeBackHalf bottomController;

	private boolean upDown;
	/**
	 * Creates a new flywheel
	 */
	public Flywheel() {
		topMotor = new TalonSRX(ElectricalLayout.FLYWHEEL_TOPMOTOR);
		bottomMotor = new TalonSRX(ElectricalLayout.FLYWHEEL_BOTTOMMOTOR);
		mySolenoid = new Solenoid(ElectricalLayout.FLYWHEEL_SOLENOID);
		
		//topController = new TakeBackHalf(0.00001);
		bottomController = new TakeBackHalf(0.00000002,120,1.0/150);
		
		//topCounter = new Counter(ElectricalLayout.FLYWHEEL_TOPCOUNTER);
//		topCounter.setDistancePerPulse(1);
//		topCounter.setSamplesToAverage(2);
		bottomCounter = new FlywheelCounter(ElectricalLayout.FLYWHEEL_BOTTOMCOUNTER);
	}
	
	/**
	 * Sets the flywheel's speed
	 * @param speed The desired speed of the flywheel, in rpm
	 */
	public void setSpeed(double speed) {
		bottomController.set(speed/60);
		bottomController.set(speed/60);
	}
	
	/**
	 * Returns the speed that the flywheel is moving at
	 * @return The speed of the flywheel, in rpm
	 */
	public double getSpeed() {
		try {
			return bottomCounter.getRate()*60;
		}
		catch (BadFlywheelException e){
			return e.getLastSpeed();
		}
	}
	
	/**
	 * Returns if the flywheel is at the desired speed
	 * @return True if the flywheel is within 50 rpm of the setpoint
	 */
	public boolean atSpeed(){
		try {
			if((Math.abs(bottomCounter.getRate()-bottomController.getSetpoint())<(50*60)) && (Math.abs(bottomCounter.getRate()-bottomController.getSetpoint())<(50*60))){
				return true;
			} else
				return false;
		} catch (BadFlywheelException e) {
			return false;
		}
	}
		
	public void up(){
		upDown = true;
	}
	
	public void down(){
		upDown = false;
	}
	
	public boolean getUpDown(){
		return upDown;
	}
	
	public void update() {
		double output;
		double speed;
		try {
			speed = bottomCounter.getRate();
			output = bottomController.getOutput(speed);
			bottomMotor.set(output);
			topMotor.set(-1*output);
		} catch (BadFlywheelException e) {
		}
		mySolenoid.set(upDown);
	}
}
