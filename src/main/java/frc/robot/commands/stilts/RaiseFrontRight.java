package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RaiseFrontRight extends Command {

  public void initialize() {
    Robot.pneumaticStilts.extendFrontRight();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}