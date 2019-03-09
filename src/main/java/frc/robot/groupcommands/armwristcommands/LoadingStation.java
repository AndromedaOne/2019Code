package frc.robot.groupcommands.armwristcommands;

import frc.robot.ArmPosition;

public class LoadingStation extends ArmWristMovementCommand {

  private final double shoulderPosition = 11.75;
  private final double wristPosition = 122.11;
  private final double extensionPosition = 26.75;

  protected static final ArmPosition setpoint = new ArmPosition(11.75, 26.75, 122.11);
  protected static final ArmPosition lowLimit = new ArmPosition(1.75, 23.75, 112.11);
  protected static final ArmPosition highLimit = new ArmPosition(135.16, 3, -46.58);

  public LoadingStation() {
    super(lowLimit, highLimit, setpoint);
  }
}