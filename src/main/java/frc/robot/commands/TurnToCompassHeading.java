package frc.robot.commands;

import frc.robot.Robot;

public class TurnToCompassHeading extends MoveTurnBase {
  private double heading = 0;

  public TurnToCompassHeading(double theHeading) {
    heading = theHeading;
    requires(Robot.driveTrain);
  }

  protected void initialize() {
    turnToCompassHeading(heading);
  }

}