package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;

public class RaiseFrontLegs extends Command {

  public void initialize() {
    Robot.pneumaticStilts.extendFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    if (!ButtonsEnumerated.isPressed(ButtonsEnumerated.STARTBUTTON, Robot.driveController)) {
      Robot.pneumaticStilts.stopRearLegs();
    }
    return !ButtonsEnumerated.isPressed(ButtonsEnumerated.STARTBUTTON, Robot.driveController);
  }

}