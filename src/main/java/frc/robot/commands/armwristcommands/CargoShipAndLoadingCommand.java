package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.groupcommands.armwristcommands.CargoShipCargo;
import frc.robot.groupcommands.armwristcommands.LoadingStation;

public class CargoShipAndLoadingCommand extends Command {

  @Override
  protected void initialize() {
    super.initialize();
    if (DriveClawMotorsSafely.hasBall) {
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