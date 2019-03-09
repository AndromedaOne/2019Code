package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;
import frc.robot.commands.armwristcommands.*;

public class CargoShipCargo extends ArmWristMovementCommand {
  private final double shoulderPosition = 151;
  private final double wristPosition = -98.59;
  private final double extensionPosition = 20.92;

  protected static final ArmPosition setpoint = new ArmPosition(151, 20.92, -98.59);
  protected static final ArmPosition lowLimit = new ArmPosition(141, 17.92, -108.59);
  protected static final ArmPosition highLimit = new ArmPosition(161, 23.92, -88.59);

  public CargoShipCargo() {
    super(lowLimit, highLimit, setpoint);
  }
}