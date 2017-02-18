/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mckenzierobotics.y2010.Brooke;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.Watchdog;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Brooke extends IterativeRobot {
	Control control = new Control();

	Robot robot = new Robot(control);

	Teleop driveStation = new Teleop(control);
	Autonomous auto = new Autonomous(control);

	public static Watchdog dog = edu.wpi.first.wpilibj.Watchdog.getInstance();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any hardware initialization code.
	 */
	public void robotInit() {
		dog.feed();
	}

	/**
	 * This is run when autonomous mode is started, and should be used for
	 * autonomous initialization code.
	 */
	public void autonomousInit() {
		dog.feed();
		auto.Init();
		dog.feed();

	}

	/**
	 * This function is called periodically when disabled
	 */
	public void disabledPeriodic() {
		dog.feed();
		auto.Disabled();//set autonomous mode
		dog.feed();
		robot.Periodic();//adjust hardware
		dog.feed();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		dog.feed();
		auto.Periodic();//autonomous stuff
		dog.feed();
		robot.Periodic();//adjust hardware
		dog.feed();
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		dog.feed();
		driveStation.Periodic();//telop stuff
		dog.feed();
		robot.Periodic();//adjust hardware
		dog.feed();
	}
}
