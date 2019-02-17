package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RaiseRearLeft extends Command {

  public void initialize() {
    Robot.pneumaticStilts.extendRearLeft();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}