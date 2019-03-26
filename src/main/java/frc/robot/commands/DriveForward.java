package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;

public class DriveForward extends Command {
  /**
   * DriveForward drives the Robot forward at a set speed for tesing purposes, in
   * order to reduce human error being a variable in a testing situation.
   */
  public DriveForward() {
    requires(Robot.driveTrain);
    System.out.println("Driving forward automatically");
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("DriveForward");
  }

  @Override
  protected void execute() {
    Robot.driveTrain.move(.5, 0, false);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("DriveForward");
    Robot.driveTrain.move(0, 0, false);
  }

  @Override
  protected void interrupted() {
    end();
  }

}