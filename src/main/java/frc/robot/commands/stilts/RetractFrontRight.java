package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractFrontRight extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractFrontRight();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}