package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RaiseFrontLeft extends Command {

  public void initialize() {
    Robot.pneumaticStilts.extendFrontLeft();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}