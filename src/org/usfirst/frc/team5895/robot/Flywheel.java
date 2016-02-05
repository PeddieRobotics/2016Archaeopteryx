package src.org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Spark;

public class Flywheel {

	Spark myMotor;
	PID myController;
	Counter c;
	private double Kp;
	private double Ki;
	private double Kd;
	private double dV;
	
	public Flywheel(){
		myMotor = new Spark(0);
		myController = new PID(Kp, Ki, Kd, dV);
		c = new Counter(8);
		c.setDistancePerPulse(1);
		c.setSamplesToAverage(2);
		
	}
	
	public void setSpeed(double speed){
		myController.set(speed/60);
	}
	
	public void update() {
		myMotor.set(myController.getOutput(c.getRate()));
		
	}

}
