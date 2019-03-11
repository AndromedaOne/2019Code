package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class HighHatch extends ArmWristMovementCommand {

  protected static final ArmPosition setpoint = new ArmPosition(126.16, 0, -89.39);

  public HighHatch() {
    super(setpoint, true);
  }
}