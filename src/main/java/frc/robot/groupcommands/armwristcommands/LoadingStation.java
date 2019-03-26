package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class LoadingStation extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(108, 28, 0);

  public LoadingStation() {
    super(setpoint, true);
  }
}