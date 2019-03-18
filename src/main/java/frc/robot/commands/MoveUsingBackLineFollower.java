package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.LineFollowerController;
import frc.robot.telemetries.Trace;

/**
 *
 */
public class MoveUsingBackLineFollower extends Command {
  private LineFollowerController controller;
  private int counter = 0;

  public MoveUsingBackLineFollower() {
    requires(Robot.driveTrain);
  }

  @Override
  protected void initialize() {
    // controller.initialize();
    Trace.getInstance().logCommandStart("MoveUsingBackLineFollower");
  }

  @Override
  protected void execute() {
    // SmartDashboard.putNumber("Counter", counter++);
    // controller.run();
  }

  @Override
  protected boolean isFinished() {
    return false;// controller.isDone();
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("MoveUsingBackLineFollower");
  }
}