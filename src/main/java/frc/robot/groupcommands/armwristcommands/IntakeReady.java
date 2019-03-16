package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class IntakeReady extends ArmWristMovementCommand {

  protected static final ArmPosition setpoint = new ArmPosition(62.5, 21.9, -38.6);

  public IntakeReady() {
    super(setpoint, false);
  }
}