package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class LowHatch extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(45, 13, 57);

  public LowHatch() {
    super(setpoint, true);
  }
}