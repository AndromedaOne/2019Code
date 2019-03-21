package frc.robot.closedloopcontrollers;

import frc.robot.Robot;

/**
 * Drives the claw motors safely
 */
public class DriveClawMotorsSafely {
  /**
   * Drives the claw intake motors safely per the specifications
   * 
   * @param speed The speed to drive the claw at
   */
  public static void DriveClawMotors(double speed) {
    Robot.claw.driveIntakeMotors(speed);
  }
}
