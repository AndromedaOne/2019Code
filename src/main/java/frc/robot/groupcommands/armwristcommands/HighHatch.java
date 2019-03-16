package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class HighHatch extends ArmWristMovementCommand {

  protected static final ArmPosition setpoint = new ArmPosition(170.9, 0, -63.0);

  public HighHatch() {
    super(setpoint, true);
  }
}