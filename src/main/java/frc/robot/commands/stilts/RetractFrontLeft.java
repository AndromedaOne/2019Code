package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractFrontLeft extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractFrontLeft();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}