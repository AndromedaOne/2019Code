package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class HighHatch extends ArmWristMovementCommand {

  protected static final ArmPosition setpoint = new ArmPosition(126.16, 0, -89.39);
  protected static final ArmPosition lowLimit = new ArmPosition(116.16, -3, -99.39);
  protected static final ArmPosition highLimit = new ArmPosition(136.16, 3, -79.39);

  public HighHatch() {
    super(lowLimit, highLimit, setpoint);
  }
}