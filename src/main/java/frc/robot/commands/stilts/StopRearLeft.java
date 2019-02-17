package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopRearLeft extends Command {

  public void initialize() {
    Robot.pneumaticStilts.stopRearLeft();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}