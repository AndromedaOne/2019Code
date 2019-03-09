package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.groupcommands.autopaths.AutoStartingConfig;

public class TurnToFieldCenter extends MoveTurnBase {
  private static GyroPIDController gyroPID = GyroPIDController.getInstance();

  public TurnToFieldCenter() {
    requires(Robot.driveTrain);
  }

  protected void initialize() {
    if (AutoStartingConfig.onRightSide) {
      turnToCompassHeading(270);
    } else {
      turnToCompassHeading(90);
    }
  }

}