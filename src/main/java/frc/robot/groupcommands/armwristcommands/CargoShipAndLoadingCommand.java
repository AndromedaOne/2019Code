package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class CargoShipAndLoadingCommand extends CommandGroup {
  public CargoShipAndLoadingCommand() {
    boolean positiveWrist = Robot.positiveWrist();
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (DriveClawMotorsSafely.hasBall) {
      addSequential(new CargoShipCargo(positiveWrist, sameSidePlacement));
    } else {
      addSequential(new LoadingStation(positiveWrist, sameSidePlacement));
    }
  }
}