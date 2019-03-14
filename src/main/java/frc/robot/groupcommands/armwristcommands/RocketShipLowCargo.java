package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class RocketShipLowCargo extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(-10.73, 25.6, 91.12);

  public RocketShipLowCargo() {
    super(setpoint, true);
  }
}