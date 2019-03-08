package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.RollIntakeIn;
import frc.robot.groupcommands.armwristcommands.IntakeReady;

public class RollIntakeGroupCommand extends CommandGroup {

  public RollIntakeGroupCommand() {
    boolean positiveShoulder = Robot.shoulderEncoder.getDistanceTicks() > 0;
    addSequential(new IntakeReady(positiveShoulder));
    //addSequential(new RollIntakeIn());
  }
}