package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class IntakeReady extends ArmWristMovementCommand {

  public static final ArmPosition setpoint = new ArmPosition(74.69, 17.278, -35.2);

  public IntakeReady() {
    super(setpoint, false);
  }
}