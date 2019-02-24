package frc.robot.closedloopcontrollers;

import frc.robot.Robot;
import frc.robot.sensors.limitswitchsensor.LimitSwitchSensor.IsAtLimitException;

public class MoveIntakeSafely {
  /**
   * Moves the intake to the speed of value. If the intake limit switch is pressed
   * then the angle sensor is reset. If limit switch is hit and intake is still
   * trying to go up, then the exeption is thrown.
   * 
   * @param value
   * @throws IsAtLimitException
   */
  public static void moveIntake(double value) throws IsAtLimitException {
    // If the intake is at the limit
    if (Robot.intakeStowedSwitch.isAtLimit()) {
      Robot.intakeAngleSensor.reset();
      // If the intake is trying to go down
      if (value < 0) {
        throw Robot.intakeStowedSwitch.new IsAtLimitException();
      }
    }
    Robot.intake.moveIntakeArm(value);
  }
}