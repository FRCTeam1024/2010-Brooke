/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckenzierobotics.y2010.Brooke;

/**
 * This is ud for debuggin output.  See, stdout is SLOW, so we should be able to
 * disable it in one place, or toggle varios messages, all from here.
 *
 * @author lukeshu
 */
public class Debug {
	/** enable System.out debugging  */public static boolean DEBUG     = true;
	/** print front kicker      mode */public static boolean fKickMode = false;
	/** print  side kicker      mode */public static boolean sKickMode = true;
	/** print  side kick loader mode */public static boolean loadMode  = true;
	/** print  side kick latch  mode */public static boolean latchMode = true;

	/** enable logging data to files */public static boolean dataLog=false;

	/**
	 * Print a line.
	 * This will print to System.out if DEBUG is true.
	 *
	 * @param str The message to print
	 */
	public static void println(String str) {
		if (DEBUG) {
			System.out.println(str);
		}
	}

	/**
	 * Conditionally print a line.
	 * This will print to System.out if the condition and DEBUG are true.
	 *
	 * @param con The condition
	 * @param str The message to print
	 */
	public static void conprintln(boolean con, String str) {
		if (con)
			println(str);
	}
}
