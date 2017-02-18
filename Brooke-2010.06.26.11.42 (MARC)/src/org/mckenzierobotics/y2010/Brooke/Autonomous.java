/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckenzierobotics.y2010.Brooke;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * The autonomous AI
 * 
 * @author FRC1024
 */
public class Autonomous {
	private final Control C;
	private final RALFF ralff;
	private DriverStation Driver = DriverStation.getInstance();
	
	private int    stage = 0; //which stage the auto mode is on
	private int autoMode = 1; //0 = do nothing
	                          //1 = Zone 1 (drive backward)
	                          //2 = Zone 2 (drive sideways & kick)
	                          //3 = Zone 3 (drive sideways & kick)

	/**
	 * Create a new instance of the autonomous controller
	 *
	 * @param C
	 */
	public Autonomous(Control C) {
		this.C = C;
		ralff = new RALFF(C);
	}

	/**
	 * Run this while in disabled mode -- it will check the switches on the
	 * driver station to see which mode to be in.
	 */
	public void Disabled() {
		     if (!Driver.getDigitalIn(8) && !Driver.getDigitalIn(7))autoMode=0;
		else if (!Driver.getDigitalIn(8) &&  Driver.getDigitalIn(7))autoMode=1;
		else if ( Driver.getDigitalIn(8) && !Driver.getDigitalIn(7))autoMode=2;
		else if ( Driver.getDigitalIn(8) &&  Driver.getDigitalIn(7))autoMode=3;
		C.lcd.lines[1]="Autonomous: "+autoMode;
	}

	/**
	 * Run this once when autonomous mode starts.
	 */
	public void Init() {
		stage=0;
	}
	/**
	 * Update C based on the autonomous AI.  This should be run periodically.
	 *
	 * @param C
	 */
	public void Periodic() {
		C.lcd.lines[1]="autoMode: "+autoMode;

		switch (autoMode) {
			case 0://Testing mode///////////////////////////////////////////////
				switch (stage) {
					case 0: if (ralff.init()/*   Misc. , Time*/) stage++; break;
					case 1: break;
				}
				break;
			case 1://Zone 1/////////////////////////////////////////////////////
				switch (stage) {
					case 0: if (ralff.init()/*   Misc. , Time*/) stage++; break;
					case 1: if (ralff.driveForward( -1 , 1   ) ) stage++; break;
					case 2: if (ralff.turnBack(0.5,-1  , 1.65) ) stage++; break;
					case 3: if (ralff.driveForward( -1 , 0.25) ) stage++; break;
					case 4: break;
				}
				break;
			case 2://Zone 2/////////////////////////////////////////////////////
				switch (stage) {
					case 0: if (ralff.init()/*   Misc. , Time*/) stage++; break;
					case 1: if (ralff.kickLoad(          1   ) ) stage++; break;
					case 2: if (ralff.driveForward(0.2 , 3.33) ) stage++; break;
					case 3: if (ralff.kick(C.kickZone3 , 1   ) ) stage++; break;
					case 4: if (ralff.driveForward(0.2 , 3.75) ) stage++; break;
					case 5: if (ralff.kick(C.kickZone3 , 1   ) ) stage++; break;
					case 6: if (ralff.extend()                 ) stage++; break;
					case 7: if (ralff.driveSide(-1,-1,   3.5 ) ) stage++; break;
					case 8: if (ralff.retract()                ) stage++; break;
					case 9: break;
				}
				break;
			case 3://Zone 3/////////////////////////////////////////////////////
				switch (stage) {
					case 0: if (ralff.init()/*   Misc. , Time*/) stage++; break;
					case 1: if (ralff.kickLoad(          1   ) ) stage++; break;
					case 2: if (ralff.driveForward(0.2 , 1.5 ) ) stage++; break;
					case 3: if (ralff.kick(C.kickZone3 , 1   ) ) stage++; break;
					case 4: if (ralff.driveForward(0.2 , 3.33) ) stage++; break;
					case 5: if (ralff.kick(C.kickZone3 , 1   ) ) stage++; break;
					case 6: if (ralff.driveForward(0.2 , 3.5 ) ) stage++; break;
					case 7: if (ralff.kick(C.kickZone3 , 1   ) ) stage++; break;
					case 8: break;
				}
				break;
		}
	}
}
