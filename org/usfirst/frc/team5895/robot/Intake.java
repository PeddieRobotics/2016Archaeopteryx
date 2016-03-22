package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Intake {
	
	private TalonSRX intakeMotor;
	private Solenoid upDownSolenoid;
	private DigitalInput sensor; 
	private boolean upDown;
	private double shootTimeStamp;
	private double ballTimeStamp;
	private boolean lastHasBall;
	private enum Mode_Type {INTAKING, REVERSE, HAS_BALL, SHOOTING};
	private Mode_Type mode = Mode_Type.INTAKING;
	
	public Intake() {
		intakeMotor = new TalonSRX(ElectricalLayout.INTAKE_MOTOR);
		upDownSolenoid = new Solenoid(ElectricalLayout.INTAKE_SOLENOID);
		sensor = new DigitalInput(ElectricalLayout.INTAKE_SENSOR);
		upDown = false;
		shootTimeStamp = Double.MIN_VALUE;
		ballTimeStamp = Double.MIN_VALUE;
		lastHasBall = false;
	}
	
	/**
	 * Moves the arm up
	 */
	public void up() {
		upDown = false;
	}
	/**
	 * Moves the arm down
	 */
	public void down() {
		upDown = true;
	}
	
	/**
	 * Shoots the ball
	 */
	public void shoot() {
			shootTimeStamp = Timer.getFPGATimestamp();
		mode = Mode_Type.SHOOTING;
	}
	/**
	 * Returns whether or not the intake is down
	 * 
	 * @return True if the intake is down, false otherwise
	 */
	public boolean isDown() {
		return upDown;
	}
	
	/**
	 * Return whether or not the intake has a ball
	 * 
	 * @return True if there is a ball, false otherwise
	 */
	public boolean hasBall() {
		return (mode == Mode_Type.HAS_BALL);
	}
	
	//REVERSE INTAKE
	public void out() {
		mode = Mode_Type.REVERSE;
	}
	
	//INTAKE
	public void in() {
		mode = Mode_Type.INTAKING;
	}
	
	public void update() {
		switch (mode) {
		
		case INTAKING:
			intakeMotor.set(0.7);
			if((lastHasBall == false) && (sensor.get() == true)){
				ballTimeStamp = Timer.getFPGATimestamp();
				mode = Mode_Type.HAS_BALL;
			}
		break;
		
		case REVERSE:
			intakeMotor.set(0.7);
			
		case HAS_BALL:
			if((Timer.getFPGATimestamp() - ballTimeStamp) < 0.1) {
				intakeMotor.set(-0.4);
			}
			else intakeMotor.set(0);
		break;
		
		case SHOOTING:
			if(Timer.getFPGATimestamp()-shootTimeStamp < 0.5){
				intakeMotor.set(0.75);
			}
			else mode = Mode_Type.INTAKING;
		break;
		}
		
		
		upDownSolenoid.set(upDown);
		lastHasBall = sensor.get();
	}

	
}
