package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;

public class LowGamePieceArmCommand extends Command {
  public LowGamePieceArmCommand() {
  }

  @Override
  protected void initialize() {
    super.initialize();
    System.out.println("Creating Low Game Piece command");

    if (DriveClawMotorsSafely.hasBall) {
      (new RocketShipLowCargo()).start();
    } else {
      System.out.println("creating low hatch command");
      (new LowHatch()).start();
      ;
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}