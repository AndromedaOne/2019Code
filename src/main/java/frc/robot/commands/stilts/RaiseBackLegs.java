package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;
import frc.robot.utilities.POVDirectionNames;

public class RaiseBackLegs extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    if (!ButtonsEnumerated.isPressed(ButtonsEnumerated.BACKBUTTON,Robot.driveController)) {
      Robot.pneumaticStilts.stopRearLegs();
    }
    return !ButtonsEnumerated.isPressed(ButtonsEnumerated.BACKBUTTON, Robot.driveController);
  }

}