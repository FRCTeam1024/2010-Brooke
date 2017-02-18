/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckenzierobotics.y2010.Brooke;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Like an AI, but read from the drive station.
 *
 * @author tjroche LOLCAT lukeshu poejreed
 */
public class Teleop {
	//************************************************************************//
	//                      Declarations and Instantiations                   //
	//************************************************************************//
	private Control C;
	private boolean joystickFix=false;
	private Joystick rJoystick = new Joystick(1);
	private Joystick lJoystick = new Joystick(2);

	private double rJoystickY;
	private double lJoystickY;
	
	DriverStation Driver = DriverStation.getInstance();
	
	private boolean button_kickKick=false;
	private boolean button_kickLoad=false;
	private boolean button_kickAuto=false;

	private boolean button_kickZone1=false;
	private boolean button_kickZone2=false;
	private boolean button_kickZone3=false;

	private double dial_right=0;
	private double dial_left=0;

	/**
	 * Create a new instance of the Teleoperated controller.
	 * 
	 * @param C
	 */
	public Teleop(Control C) {
		this.C = C;
	}
	/**
	 * Read from the drive station to update the control structure.
	 *
	 * @param C control structure
	 */
	public void Periodic() {
		//********************************************************************//
		//                           Update Buttons                           //
		//********************************************************************//
		button_kickKick  =(!Driver.getDigitalIn(2));
		button_kickLoad  =(!Driver.getDigitalIn(4));
		button_kickAuto  =(!Driver.getDigitalIn(6));
		button_kickZone3 =(!Driver.getDigitalIn(1))||rJoystick.getRawButton(2);
		button_kickZone2 =(!Driver.getDigitalIn(3));
		button_kickZone1 =(!Driver.getDigitalIn(5));
		dial_right       =Math.min((Driver.getAnalogIn(1)/666),1);
		//dial_left        =Math.min((Driver.getAnalogIn(2)/3.25),1);

		//********************************************************************//
		//                     Operator Control for Kicker                    //
		//********************************************************************//
		C.kickAuto= (button_kickAuto
		           ||button_kickZone1
		           ||button_kickZone2
		           ||button_kickZone3);

		     if (button_kickAuto ) C.kickSpeed=dial_right;
		else if (button_kickKick ) C.kickSpeed=dial_right;
		else if (button_kickZone1) C.kickSpeed=C.kickZone1;
		else if (button_kickZone2) C.kickSpeed=C.kickZone2;
		else if (button_kickZone3) C.kickSpeed=C.kickZone3;

		C.kickKick=button_kickKick;
		C.kickLoad=button_kickLoad;

		//********************************************************************//
		//                         Fix Joysticks                              //
		//********************************************************************//
		if (rJoystick.getRawButton(3)) {
			if (!joystickFix) {
				Joystick tempJoy=lJoystick;
				lJoystick=rJoystick;
				rJoystick=tempJoy;
				joystickFix = true;
			}
		} else {
			joystickFix=false;
		}
		
		//********************************************************************//
		//                 Operator Control for Drive System                  //
		//********************************************************************//
		// joysticks are crazy and return opposite of what you would expect
		rJoystickY=-rJoystick.getY();
		lJoystickY=-lJoystick.getY();
		if (lJoystick.getTrigger() && rJoystick.getTrigger()) {
			// both pressed
			C.lDrive = C.rDrive = 0;
			C.upFront= C.upBack = true;
			C.bDrive = rJoystickY;
			C.fDrive = lJoystickY;
		} else if (lJoystick.getTrigger()) {
			//left pressed
			C.upFront= true;
			C.upBack = false;
			C.lDrive = lJoystickY;
			C.rDrive = rJoystickY;
			C.fDrive = (lJoystickY-rJoystickY)/2;
			C.bDrive = 0;
		} else if (rJoystick.getTrigger()) {
			//right pressed
			C.upFront= false;
			C.upBack = true;
			C.lDrive = lJoystickY;
			C.rDrive = rJoystickY;
			C.fDrive = 0;
			C.bDrive = (rJoystickY-lJoystickY)/2;
		} else {
			//none pressed
			C.fDrive =C.bDrive=0;
			C.upFront=C.upBack=false;
			C.rDrive =C.lDrive=(rJoystickY+lJoystickY)/2;//average
		}

		//********************************************************************//
		//                         DriverStationLCD                           //
		//********************************************************************//

		/*====================================================================*\
		||         NOTE: USE `C.whatever' VARIABLES WHENEVER POSSIBLE         ||
		\*====================================================================*/

		C.lcd.lines[1]="kPower:"+Math.floor(dial_right*100);//NOT `C.kickSpeed'
		/* C.kickSpeed is only updated when a button is pressed, not when the
		 * dial is turned.  Additionally, if the operator presses a kick preset
		 * button, this C.kickSpeed will be the preset, not the value of the
		 * dial.  No one really cares what the current C.kickSpeed is, they want
		 * to see what they have the dial set to.
		 */
	}
	
}
