package org.usfirst.frc.team5895.robot;

import org.usfirst.frc.team5895.robot.FlywheelCounter.BadFlywheelException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel {

	private TalonSRX topMotor;
	private TalonSRX bottomMotor;
	private Solenoid mySolenoid;
	
	private FlywheelCounter topCounter;
	private FlywheelCounter bottomCounter;
	
//	private TakeBackOnce topController;
//	private TakeBackOnce bottomController;
	private TakeBackHalf topController;
	private TakeBackHalf bottomController;
//	private BangBang topController;
//	private BangBang bottomController;

	private boolean upDown;
	
	private enum Mode_Type {AUTO_SHOOT, OVERRIDE};
	
	private Mode_Type mode = Mode_Type.AUTO_SHOOT;
	
	private int atSpeed;
	private double lastTime;
	double overrideSpeed;
	
	/**
	 * Creates a new flywheel
	 */
	public Flywheel() {
		topMotor = new TalonSRX(ElectricalLayout.FLYWHEEL_TOPMOTOR);
		bottomMotor = new TalonSRX(ElectricalLayout.FLYWHEEL_BOTTOMMOTOR);
		mySolenoid = new Solenoid(ElectricalLayout.FLYWHEEL_SOLENOID);
		
		topController = new TakeBackHalf(0.00005, 6150/60, 1.0);
		bottomController = new TakeBackHalf(0.00005, 6050/60, 1.0);
		
//		topController = new TakeBackOnce(0.00005,6050.0/60);
//		bottomController = new TakeBackOnce(0.00005,6000.0/60);
		
//		topController = new BangBang();
//		bottomController = new BangBang();
			
		
		topCounter = new FlywheelCounter(ElectricalLayout.FLYWHEEL_TOPCOUNTER);
		bottomCounter = new FlywheelCounter(ElectricalLayout.FLYWHEEL_BOTTOMCOUNTER);
		
		atSpeed = 0;
		lastTime = Timer.getFPGATimestamp();
	}
	
	/**
	 * Sets the flywheel's speed
	 * @param speed The desired speed of the flywheel, in rpm
	 */
	public void setSpeed(double speed) {
		mode = Mode_Type.AUTO_SHOOT;
		atSpeed = 0;
		
		bottomController.set(speed/60);
		
		topController.set(speed/60);
	}
	
	/**
	 * Sets the flywheel's speed
	 * @param topSpeed The desired speed of the top flywheel, in rpm
	 * @param bottomSpeed The desired speed of the top flywheel, in rpm
	 */
	public void setSpeed(double topSpeed, double bottomSpeed) {
		mode = Mode_Type.AUTO_SHOOT;
		atSpeed = 0;
		bottomController.set(bottomSpeed/60);
		topController.set(topSpeed/60);
		
	}
	
	public void override(double speed){
		mode = Mode_Type.OVERRIDE;
		overrideSpeed = speed;
	}
	
	public void lock(){
//		topController.lock();
//		bottomController.lock();
	}
	
	/**
	 * Returns the speed that the flywheel is moving at
	 * @return The speed of the flywheel, in rpm
	 */
	public double getTopSpeed() {
		try {
			return topCounter.getRate()*60;
		}
		catch (BadFlywheelException e){
			return e.getLastSpeed();
		}
	}
	
	public double getBottomSpeed() {
		try {
			return bottomCounter.getRate()*60;
		}
		catch (BadFlywheelException e){
			return e.getLastSpeed();
		}
	}
	
	/**
	 * Returns if the flywheel is at the desired speed
	 * @return True if the flywheel is within 25 rpm of the setpoint for the last 50ms
	 */
	public boolean atSpeed(){
		return atSpeed > 75;
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
		double bottomOutput;
		double topOutput;
		double bottomSpeed;
		double topSpeed;
		try {
			bottomSpeed = bottomCounter.getRate();
			topSpeed = topCounter.getRate();
			
		DriverStation.reportError("bottom:" + bottomSpeed*60+" top:" + topSpeed*60 +"\n", false);
	
			
			bottomOutput = bottomController.getOutput(bottomSpeed);
			topOutput = topController.getOutput(topSpeed);
			switch (mode){
			case AUTO_SHOOT:
				bottomMotor.set(-bottomOutput);
				topMotor.set(topOutput);
			break;
			case OVERRIDE:
				bottomMotor.set(-overrideSpeed);
				topMotor.set(overrideSpeed);
				break;
			}
			double dt = (Timer.getFPGATimestamp() - lastTime)*1000;
			lastTime = Timer.getFPGATimestamp();
			if (Math.abs(bottomSpeed-bottomController.getSetpoint()) < 15.0/60 &&
					Math.abs(topSpeed-topController.getSetpoint()) < 15.0/60) {
				atSpeed += dt;
			} else {
				atSpeed = 0;
			}
		} catch (BadFlywheelException e) {
		} 
		mySolenoid.set(upDown);
	}
}
