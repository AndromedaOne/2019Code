package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RaiseFrontLegs extends Command {

  public void initialize() {
    Robot.pneumaticStilts.extendFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    return false;// !POVDirectionNames.getPOVEast(Robot.driveController); This needs a button
                 // assignment
  }

}