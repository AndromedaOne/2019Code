package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.groupcommands.armwristcommands.MiddleCargo;
import frc.robot.groupcommands.armwristcommands.MiddleHatch;

public class MiddleGamePieceArmCommand extends Command {
  public MiddleGamePieceArmCommand() {

    if (DriveClawMotorsSafely.hasBall) {
      (new MiddleCargo()).start();
    } else {
      (new MiddleHatch()).start();
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}