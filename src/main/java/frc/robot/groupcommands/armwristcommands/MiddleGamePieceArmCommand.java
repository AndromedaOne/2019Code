package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;

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