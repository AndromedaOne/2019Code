package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.groupcommands.autopaths.AutoStartingConfig;

public class TurnToRocketBay3 extends MoveTurnBase {

  public TurnToRocketBay3() {
    requires(Robot.driveTrain);
  }

  protected void initialize() {
    if (AutoStartingConfig.onRightSide) {
      turnToCompassHeading(45);
    } else {
      turnToCompassHeading(315);
    }
  }

}