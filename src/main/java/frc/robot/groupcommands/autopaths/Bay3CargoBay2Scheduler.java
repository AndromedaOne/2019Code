package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.Command;

/**
 * When this is selected, it automatically runs Bay3CargoBay2().
 */
public class Bay3CargoBay2Scheduler extends Command {

  protected void initialize() {
    (new Bay3CargoBay2()).start();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}