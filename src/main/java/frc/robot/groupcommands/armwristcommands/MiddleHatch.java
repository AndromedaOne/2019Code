package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class MiddleHatch extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(147, 25.234, -57);

  public MiddleHatch() {
    super(setpoint, true);
  }
}