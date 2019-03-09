package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class PickUpHatchFromGround extends ArmWristMovementCommand {
  protected static ArmPosition setpoint = new ArmPosition(50, 28, -50);
  protected static ArmPosition lowerLimit = new ArmPosition(40, 20, -60);
  protected static ArmPosition upperLimit = new ArmPosition(60, 35, -40);

  public PickUpHatchFromGround() {
    super(lowerLimit, upperLimit, setpoint);
  }

}