package org.usfirst.frc.team5895.robot;

public class ElectricalLayout {
	
	//Motors
	public static final int DRIVE_LEFTMOTOR = 1;
	public static final int DRIVE_RIGHTMOTOR = 0;
	public static final int FLYWHEEL_TOPMOTOR = 5;
	public static final int FLYWHEEL_BOTTOMMOTOR = 4;
	public static final int INTAKE_MOTOR = 2;
	public static final int TURRET_MOTOR = 3;

	//Solenoids
	public static final int ARM_SOLENOID = 3; //Cheval de Frise
	public static final int INTAKE_SOLENOID = 1;
	public static final int FLYWHEEL_SOLENOID = 2; //Shooter Hood
	
	
	//Digital Inputs
	public static final int LEFTDRIVE_ENCODER = 0;
	public static final int LEFTDRIVE_ENCODER2 = 1;
	public static final int RIGHTDRIVE_ENCODER = 2;
	public static final int RIGHTDRIVE_ENCODER2 = 3;
	public static final int FLYWHEEL_TOPCOUNTER = 4;
	public static final int FLYWHEEL_BOTTOMCOUNTER = 5;
	public static final int INTAKE_SENSOR = 6;
	public static final int TURRET_ENCODER = 7;
	public static final int TURRET_ENCODER2 = 8;	
}
