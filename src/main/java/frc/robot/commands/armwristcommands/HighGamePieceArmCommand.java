package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.groupcommands.armwristcommands.HighCargo;
import frc.robot.groupcommands.armwristcommands.HighHatch;

public class HighGamePieceArmCommand extends Command {
  public HighGamePieceArmCommand() {
    if (Robot.robotHasBall()) {
      (new HighCargo()).start();
    } else {
      (new HighHatch()).start();
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}