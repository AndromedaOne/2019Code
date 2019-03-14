package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class MiddleCargo extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(175.52, 18.9, -82.95);

  public MiddleCargo() {
    super(setpoint, true);
  }
}