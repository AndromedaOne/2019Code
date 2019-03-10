package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class MiddleCargo extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(175.52, 18.9, -82.95);
  protected static final ArmPosition lowLimit = new ArmPosition(165.52, 15.9, -92.95);
  protected static final ArmPosition highLimit = new ArmPosition(185.52, 21.9, -72.95);

  public MiddleCargo() {
    super(lowLimit, highLimit, setpoint);
  }
}