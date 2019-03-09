package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class LowHatch extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(-4, 11.8, 95.5);
  protected static final ArmPosition lowLimit = new ArmPosition(-14, 8.8, 85.5);
  protected static final ArmPosition highLimit = new ArmPosition(6, 14.8, 105.5);

  public LowHatch() {
    super(lowLimit, highLimit, setpoint);
  }
}