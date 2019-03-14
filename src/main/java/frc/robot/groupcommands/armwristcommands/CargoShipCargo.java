package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;
import frc.robot.commands.armwristcommands.*;

public class CargoShipCargo extends ArmWristMovementCommand {

  protected static final ArmPosition setpoint = new ArmPosition(151, 20.92, -98.59);

  public CargoShipCargo() {
    super(setpoint, true);
  }
}