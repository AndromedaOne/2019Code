package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.groupcommands.armwristcommands.CargoShipCargo;
import frc.robot.groupcommands.armwristcommands.LoadingStation;

public class CargoShipAndLoadingCommand extends Command {

  @Override
  protected void initialize() {
    super.initialize();
    if (Robot.robotHasBall()) {
      (new CargoShipCargo()).start();

    } else {
      (new LoadingStation()).start();
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}