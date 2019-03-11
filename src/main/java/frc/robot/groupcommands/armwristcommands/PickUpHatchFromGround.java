package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class PickUpHatchFromGround extends ArmWristMovementCommand {
  protected static ArmPosition setpoint = new ArmPosition(50, 28, -50);

  public PickUpHatchFromGround() {
    super(setpoint, true);
  }

}