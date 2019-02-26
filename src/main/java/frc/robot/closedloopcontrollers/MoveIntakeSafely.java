package frc.robot.closedloopcontrollers;

import frc.robot.Robot;

public class MoveIntakeSafely {
  /**
   * Moves the intake to the speed of value. If the intake limit switch is pressed
   * then the angle sensor is reset. If limit switch is hit and intake is still
   * trying to go up, then the exeption is thrown.
   * 
   * @param value
   */
  public static void moveIntake(double value) {
    // If the intake is at the limit
    if (Robot.intakeStowedSwitch.isAtLimit()) {
      Robot.intakeAngleSensor.reset();
      // If the intake is trying to go down
      if (value > 0) {
        value = 0;
      }
    }
    Robot.intake.moveIntakeArm(value);
  }
}