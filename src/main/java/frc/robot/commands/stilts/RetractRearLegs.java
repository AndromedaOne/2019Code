package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;
import frc.robot.utilities.POVDirectionNames;

public class RetractRearLegs extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractRearLegs();
  }

  @Override
  protected boolean isFinished() {
    return !ButtonsEnumerated.isPressed(POVDirectionNames.WEST, OI.getInstance().getDriveStick());
  }
}