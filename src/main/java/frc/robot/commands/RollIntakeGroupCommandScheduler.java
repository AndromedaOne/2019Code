package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.groupcommands.RollIntakeGroupCommand;

public class RollIntakeGroupCommandScheduler extends Command {

  @Override
  protected void initialize() {
    super.initialize();
    (new RollIntakeGroupCommand()).start();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}