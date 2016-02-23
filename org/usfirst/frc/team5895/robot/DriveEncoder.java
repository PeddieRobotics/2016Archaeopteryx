package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class DriveEncoder {
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	public DriveEncoder() {
		leftEncoder = new Encoder(ElectricalLayout.LEFTDRIVE_ENCODER, ElectricalLayout.LEFTDRIVE_ENCODER2, false, EncodingType.k4X);
		rightEncoder = new Encoder(ElectricalLayout.RIGHTDRIVE_ENCODER, ElectricalLayout.RIGHTDRIVE_ENCODER, false, EncodingType.k4X);
		
		leftEncoder.setDistancePerPulse(4*Math.PI/360);
		rightEncoder.setDistancePerPulse(4*Math.PI/360);
		
	}
		public void reset(){
			leftEncoder.reset();
			rightEncoder.reset();
		}
		
		public double getDistance(){
			return ((leftEncoder.getDistance()+rightEncoder.getDistance())/2);
		}
		
	

}
