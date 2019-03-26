package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.Command;

/**
 * When this is selected, it automatically runs CenterBaysFromMiddle().
 */
public class CenterBaysFromMiddleScheduler extends Command {

  protected void initialize() {
    (new CenterBaysFromMiddle()).start();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}