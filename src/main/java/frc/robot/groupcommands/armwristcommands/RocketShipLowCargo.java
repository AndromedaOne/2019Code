package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class RocketShipLowCargo extends ArmWristMovementCommand {
  protected static final ArmPosition setpoint = new ArmPosition(-10.73, 25.6, 91.12);
  protected static final ArmPosition lowLimit = new ArmPosition(-20.73, 23.6, 81.12);
  protected static final ArmPosition highLimit = new ArmPosition(-0.73, 28.6, 101.12);

  public RocketShipLowCargo() {
    super(lowLimit, highLimit, setpoint);
  }
}