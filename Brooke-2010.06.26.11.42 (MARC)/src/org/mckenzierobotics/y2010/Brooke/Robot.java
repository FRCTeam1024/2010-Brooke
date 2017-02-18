/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckenzierobotics.y2010.Brooke;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.addons.CANJaguar;

/**
 * The physical robot hardware and hardware functions.
 *
 * @author tjroche lukeshu LOLCAT poejreed
 */
public class Robot {
	private final Control C;

	private final Compressor comp = new Compressor(1, 1);

	//Kicker////////////////////////////////////////////////////////////////////
	private final CANJaguar kicker = new CANJaguar(2);
	private final Timer kTimer = new Timer();
	private boolean kickAuto=false;
	private double kickTime=-1;
	private double kickSpeed=1;
	private final double kTimeKick=1;
	private final double kTimeLoad=1.75;
	private       double kTime=kTimeKick;
	private final DataLogger kLog = new DataLogger("file:///kick.log");

	//Drive/////////////////////////////////////////////////////////////////////
	private static final double rConversion = -1;
	private static final double lConversion =  1;
	private static final double fConversion =  1;
	private static final double bConversion = -1;
	private static final double fRatio = 1;
	private static final double bRatio = 1;
	
	private final CANJaguar rDrive1=new CANJaguar(4);
	private final CANJaguar rDrive2=new CANJaguar(5);
	private final CANJaguar lDrive1=new CANJaguar(7);
	private final CANJaguar lDrive2=new CANJaguar(8);

	private final CANJaguar fDrive =new CANJaguar(3);
	private final CANJaguar bDrive =new CANJaguar(6);
	
	private final Solenoid fSolenoid=new Solenoid(4);
	private final Solenoid bSolenoid=new Solenoid(5);
	
	/**
	 * Initialize the Autonomous AI
	 */
	public Robot(Control C) {
		this.C = C;
		
		kicker.configEncoderCodesPerRev((short)250);
		kTimer.start();

		fDrive.configMaxOutputVoltage(14);
		bDrive.configMaxOutputVoltage(14);
	}

	/**
	 * Update the robot hardware based on the C control struct.  This should be
	 * run periodically.
	 *
	 * @param C control struct
	 */
	public void Periodic() {
		//********************************************************************//
		//                               Kicker                               //
		//********************************************************************//
		if (C.kickLoad) {//=====================================(manual) loading
			kicker.set(-.2);
		} else if (kTimer.get()<kTimeLoad) {//===========================loading
			/* in theory we should maybe be worried about the C.kickManual here,
			 * but kTimeLoad is <2s, so rules say it's moot.
			 */
			kicker.set(-.2);
		} else if (kickTime<0) {//======================================relaxing
			kicker.set(-.1);
			// ( (==we can kick=) && (=====we want to kick====) )
			if ( (kTimer.get()>2) && (C.kickAuto||C.kickKick) ) {
				kickTime=kTimer.get();
				kickAuto=C.kickAuto;
				kickSpeed=C.kickSpeed;
				//kTime=(kTimeKick*((1-kickSpeed)/2));
				kTime=(kTimeKick*Math.min(1,1.3-kickSpeed));
			}
		} else {//=======================================================kicking
			kicker.set(kickSpeed);
			//Do we need to stop kicking? There are 3 possible reasons:
			if( (                ((kTimer.get()-kickTime)>2)     ) ||//1:rules
			    ( ( kickAuto) && ((kTimer.get()-kickTime)>kTime) ) ||//1:auto
			    ( (!kickAuto) && (!C.kickKick)                   ) ){//2:manual
				kTimer.reset();
				kickTime=-1;
			}
		}

		if (kicker.get()!=0) {
			kLog.println(";time:" +System.currentTimeMillis()
			            +";set:"  +kicker.get()
			            +";speed:"+kicker.getSpeed());
		}

		//********************************************************************//
		//                             Compressor                             //
		//********************************************************************//
		if (comp.getPressureSwitchValue())
			comp.stop();
		else
			comp.start();

		//********************************************************************//
		//                               Drive                                //
		//********************************************************************//
		/**** Omni-Wheels ****/
		fSolenoid.set(C.upFront);
		bSolenoid.set(C.upBack);
		if (C.upFront && C.upBack) {
			fDrive.set(C.fDrive*fConversion*fRatio);
			bDrive.set(C.bDrive*bConversion*bRatio);
		} else {
			//the trinary operators are to only spin that wheel if it's touching
			fDrive.set(C.upFront?(C.fDrive*fConversion):0);
			bDrive.set(C.upBack ?(C.bDrive*bConversion):0);
		}
		/**** Drive Wheels ****/
		rDrive1.set(C.rDrive*rConversion);
		rDrive2.set(C.rDrive*rConversion);
		lDrive1.set(C.lDrive*lConversion);
		lDrive2.set(C.lDrive*lConversion);
		
		//********************************************************************//
		//                         DriverStationLCD                           //
		//********************************************************************//

		/*====================================================================*\
		||         NOTE: USE `C.whatever' VARIABLES WHENEVER POSSIBLE         ||
		\*====================================================================*/

		C.lcd.lines[0]="compressor:"+(comp.getPressureSwitchValue()?"off":"on");
		//lcd.lines[1]=reserved for the controller
		C.lcd.lines[2]="";
		C.lcd.lines[3]="";
		C.lcd.lines[4]="lDrive:"+C.lDrive;
		C.lcd.lines[5]="rDrive:"+C.rDrive;
		C.lcd.update();
	}
}
