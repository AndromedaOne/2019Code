package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;
import frc.robot.commands.armwristcommands.*;

public class HighCargo extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(126.16, 0, -56.58);

  public HighCargo() {
    super(setpoint, true);
  }
}