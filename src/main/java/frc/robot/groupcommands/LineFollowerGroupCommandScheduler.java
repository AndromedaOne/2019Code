package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Schedules the GroupCommand LineFollowerGroupCommand.
 */

public class LineFollowerGroupCommandScheduler extends Command {

  @Override
  protected void initialize() {
    double lineAngle = Robot.frontLineSensor.findLine().lineAngle;
    (new LineFollowerGroupCommand(lineAngle)).start();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}