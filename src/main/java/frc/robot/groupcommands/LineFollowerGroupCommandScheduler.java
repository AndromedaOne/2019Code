package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Schedules the GroupCommand LineFollowerGroupCommand. In addition, it gets the
 * line angle from the front line sensor and gives it to the turn to delta angle
 * command sequential.
 */

public class LineFollowerGroupCommandScheduler extends Command {

  @Override
  protected void initialize() {
    System.out.println("Running...");
    double lineAngle = Robot.frontLineSensor.findLine().lineAngle;
    (new LineFollowerGroupCommand(lineAngle)).start();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}