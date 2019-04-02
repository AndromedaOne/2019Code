package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.LineFollowerController;
import frc.robot.telemetries.Trace;

/**
 *
 */
public class MoveUsingFrontLineFollower extends Command {
  private LineFollowerController controller;
  private int counter = 0;

  public MoveUsingFrontLineFollower() {
    controller = new LineFollowerController(Robot.gyroCorrectMove, Robot.frontLineSensor);
    requires(Robot.driveTrain);

  }

  @Override
  protected void initialize() {
    System.out.println("Running MUFLF");
    Trace.getInstance().logCommandStart("CallLineFollowerController");
    controller.initialize();
  }

  @Override
  protected void execute() {
    SmartDashboard.putNumber("Counter", counter++);
    controller.run();
  }

  @Override
  protected boolean isFinished() {
    return controller.isDone();
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("CallLineFollowerController");
    controller.stop();
  }

  @Override
  protected void interrupted() {
  }
}