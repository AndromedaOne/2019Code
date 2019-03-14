package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class LoadingStation extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(11.75, 26.75, 122.11);

  public LoadingStation() {
    super(setpoint, true);
  }
}