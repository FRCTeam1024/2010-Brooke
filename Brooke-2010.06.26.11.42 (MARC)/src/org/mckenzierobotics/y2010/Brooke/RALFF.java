/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckenzierobotics.y2010.Brooke;

import edu.wpi.first.wpilibj.Timer;

/**
 * RALFF - Robotics Autonomous Language For FIRST
 *
 * This gives you very general commands to use when creating autonomous.  Look
 * in Autonomous.java for examples of how to use this.
 *
 * So, it's not actually a language, because I never got around to writing an
 * interpretter (extra lame since other teams actually have interpreted
 * languages that they wrote).  But it works well enough.
 *
 * HACKING:
 * To add a new command, create a public boolean method, with any desired
 * parameters.  If it is done doing it's thing, return true, so we can go on to
 * the next step.  If it is not done doing its thing, and needs to run another
 * iteration, then return false.  You may use the class variable ``tick'' to
 * store a boolean value, however ALWAYS reset it to false before returning
 * true.  Reftime is when the previous command ended/this one started, so also
 * set `refTime=timer.get();' when returning true.
 *
 * @author luke LOLCAT
 */
public class RALFF {
	private boolean tick = false;
	private final Control C;

	public Timer timer = new Timer();
	private double refTime = 0;

	/**
	 * Create a new instance of RALFF
	 *
	 * @param C
	 */
	public RALFF(Control C) {
		this.C = C;
	}

	/**
	 * Start/reset the timers and such.  This should be run immediatly before
	 * any other RALFF commands.
	 *
	 * @return go to next step?
	 */
	public boolean init() {
		timer.start();
		refTime = timer.get();
		Debug.println("ralffinit");
		C.lcd.lines[1]="ralffinit";
		return true;
	}

	/**
	 * Exetend the omniwheels to strafe.
	 *
	 * @return go to next step?
	 */
	public boolean extend() {
		C.upBack = true;
		C.upFront = true;
		if (timer.get()>(refTime+0.6)) {
			refTime=timer.get();
			return true;
		}
		return false;
	}

	/**
	 * Turn by extending the front omniwheels.  Do this by spoofing joystick
	 * input.
	 *
	 * @param left    left ``joystick''
	 * @param right  right ``joystick''
	 * @param Time   how long to do this for
	 * @return go to next step?
	 */
	public boolean turnFront(double left, double right, double Time) {
		C.upFront= true;
		C.upBack = false;
		C.lDrive = left;
		C.rDrive = right;
		C.fDrive = (left-right)/2;
		C.bDrive = 0;
		if (timer.get()>=(refTime+Time)) {
			C.lDrive = C.rDrive = C.fDrive = 0;
			refTime=timer.get();
			return true;
		}
		return false;
	}

	/**
	 * Turn by extending the back omniwheels.  Do this by spoofing joystick
	 * input.
	 *
	 * @param left    left ``joystick''
	 * @param right  right ``joystick''
	 * @param Time   how long to do this for
	 * @return go to next step?
	 */
	public boolean turnBack(double left, double right, double Time) {
		C.upFront= false;
		C.upBack = true;
		C.lDrive = left;
		C.rDrive = right;
		C.fDrive = 0;
		C.bDrive = (right-left)/2;
		if (timer.get()>=(refTime+Time)) {
			C.lDrive = C.rDrive = C.bDrive = 0;
			refTime=timer.get();
			return true;
		}
		return false;
	}

	/**
	 * Drive forward at Speed for Time.
	 *
	 * @param Speed
	 * @param Time
	 * @return go to next step?
	 */
	public boolean driveForward(double Speed, double Time) {
		C.upBack = C.upFront = false;
		C.lDrive = C.rDrive = Speed;
		if ((timer.get()-refTime)>=Time) {
			C.lDrive = C.rDrive = 0;
			refTime=timer.get();
			return true;
		}
		return false;
	}

	/**
	 * Drive sideways by sending raw values to the Jags.
	 * (like if we were just using joysticks).
	 *
	 * @param front  Front value
	 * @param back   Back value
	 * @param Time   How long to do this for
	 * @return go to next step?
	 */
	public boolean driveSide(double front, double back, double Time) {
		C.fDrive = front;
		C.bDrive = back;
		if (timer.get()>=(refTime+Time)) {
			C.fDrive = C.bDrive = 0;
			refTime=timer.get();
			return true;
		}
		return false;
	}

	/**
	 * Retract the omniwheels, so that we can drive forward.
	 *
	 * @return go to next step?
	 */
	public boolean retract() {
		C.upBack = false;
		C.upFront = false;
		if (timer.get()>(refTime+0.6)) {
			refTime = timer.get();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Kick the kicker.
	 *
	 * @param power how hard to kick
	 * @param time  for how long we should stop+holdTheButton
	 * @return go to next step?
	 */
	public boolean kick(double power,double time) {
		C.kickSpeed=power;
		C.kickAuto=true;
		if (tick) {
			if (timer.get()>(refTime+time)) {
				C.kickAuto=false;
				tick=false;
				refTime = timer.get();
				return true;
			}
		} else {
			tick=true;
		}
		return false;
	}

	public boolean kickLoad(double time) {
		C.kickLoad=true;
		if (timer.get()>(refTime+time)) {
			C.kickLoad=false;
			tick=false;
			refTime = timer.get();
			return true;
		}
		return false;
	}

}
