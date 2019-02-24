package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;

public class TurnToCompassHeading extends TurningBase {
  private double heading = 0;
  private static GyroPIDController gyroPID = GyroPIDController.getInstance();

  public TurnToCompassHeading(double theHeading) {
    heading = theHeading;
    requires(Robot.driveTrain);
  }

  protected void initialize() {
    turnToCompassHeading(heading);
  }

  @Override
  protected boolean isFinished() {
    return gyroPID.onTarget();
  }

}