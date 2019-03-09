package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;
import frc.robot.commands.armwristcommands.*;

public class HighCargo extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(126.16, 0, -56.58);
  protected static final ArmPosition lowLimit = new ArmPosition(115, -3, -66.56);
  protected static final ArmPosition highLimit = new ArmPosition(135.16, 3, -46.58);

  public HighCargo() {
    super(lowLimit, highLimit, setpoint);
  }
}