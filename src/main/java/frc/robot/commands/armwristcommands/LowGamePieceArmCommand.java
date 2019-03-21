package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.groupcommands.armwristcommands.LowHatch;
import frc.robot.groupcommands.armwristcommands.RocketShipLowCargo;

public class LowGamePieceArmCommand extends Command {
  public LowGamePieceArmCommand() {
  }

  @Override
  protected void initialize() {
    super.initialize();
    System.out.println("Creating Low Game Piece command");

    if (Robot.robotHasBall()) {
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