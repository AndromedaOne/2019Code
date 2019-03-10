package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.groupcommands.armwristcommands.IntakeReady;

public class RollIntakeGroupCommand extends CommandGroup {

  public RollIntakeGroupCommand() {
    addSequential(new IntakeReady());
  }
}