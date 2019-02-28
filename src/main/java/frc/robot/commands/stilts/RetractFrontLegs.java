package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.POVDirectionNames;

public class RetractFrontLegs extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    return !POVDirectionNames.getPOVEast(Robot.driveController);
  }

}