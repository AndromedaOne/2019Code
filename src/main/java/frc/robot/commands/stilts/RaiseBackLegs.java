package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RaiseBackLegs extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    return false;// !POVDirectionNames.getPOVEast(Robot.driveController); This needs a button
                 // assignment
  }

}