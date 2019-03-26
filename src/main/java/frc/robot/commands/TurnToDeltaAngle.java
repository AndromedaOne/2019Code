package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.telemetries.Trace;

public class TurnToDeltaAngle extends Command {
  private GyroPIDController gyroPID = GyroPIDController.getInstance();
  private double deltaAngle;

  public TurnToDeltaAngle(double theDeltaAngle) {
    deltaAngle = theDeltaAngle;
    requires(Robot.driveTrain);
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("TurnToDeltaAngle");
    gyroPID.setSetpoint(deltaAngle);
    gyroPID.enable();
  }

  @Override
  protected boolean isFinished() {
    return gyroPID.onTarget();
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("TurnToDeltaAngle");
  }

}