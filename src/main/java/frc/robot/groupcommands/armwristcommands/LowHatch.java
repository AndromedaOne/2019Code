package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class LowHatch extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(-4, 11.8, 95.5);

  public LowHatch() {
    super(setpoint, true);
  }
}