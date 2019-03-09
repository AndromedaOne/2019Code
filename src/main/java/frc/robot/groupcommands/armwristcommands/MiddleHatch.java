package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class MiddleHatch extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(147, 25.234, -57);
  protected static final ArmPosition lowLimit = new ArmPosition(137, 23.234, -67);
  protected static final ArmPosition highLimit = new ArmPosition(157, 28.234, -47);

  public MiddleHatch() {
    super(lowLimit, highLimit, setpoint);
  }
}