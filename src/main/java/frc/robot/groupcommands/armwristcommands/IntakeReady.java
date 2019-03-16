package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class IntakeReady extends ArmWristMovementCommand {

  protected static final ArmPosition setpoint = new ArmPosition(62.83, 22.33, -38.7);

  public IntakeReady() {
    super(setpoint, false);
  }
}