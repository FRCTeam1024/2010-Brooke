/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckenzierobotics.y2010.Brooke;

/**
 * A mere struct(ure) that teleop or the AI write to, and the robot reads from.
 * This should be the high-level interface to the robot.
 * 
 * @author lukeshu poejreed tjroche LOLCAT
 */
public class Control {
	/** A usable instance of LCD, accessable from the robot or controller
	 *
	 * NOTE: Strictly, this would not go in Control, as it is not used to pass
	 * data between the controller and robot.  However, it is used tp pass data
	 * from the robot _OR_ controller to the drive-station.  This is therefore,
	 * the best place for it
	 *
	 * @see org.mckenzierobotics.y2010.Brooke.hardware.LCD
	 */
	public LCD lcd = new LCD();
	
	/** Extend the back  pneumatics?     */public boolean upBack=false;
	/** Extend the front pneumatics?     */public boolean upFront=false;

	/** Front-drive speed (`-1' through `1') */public double fDrive=0;
	/** Back-drive speed (`-1' through `1') */public double bDrive=0;
	/** Left-drive speed (`-1' through `1') */public double lDrive=0;
	/** Right-drive speed (`-1' through `1') */public double rDrive=0;

	/** Try to fire the kicker?      */public boolean kickAuto=false;
	/** Simulate a limit kick?       */public boolean kickKick=false;
	/** Manually retract the kicker? */public boolean kickLoad=false;
	/** kick speed (scale of 0-1)    */public double  kickSpeed = 1;

	/** Zone 1 kick strength (0-1)   */public final double kickZone1=.4;
	/** Zone 2 kick strength (0-1)   */public final double kickZone2=.85;
	/** Zone 3 kick strength (0-1)   */public final double kickZone3=1;
}
