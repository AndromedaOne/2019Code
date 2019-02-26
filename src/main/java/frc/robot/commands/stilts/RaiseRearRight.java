package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RaiseRearRight extends Command {

  public void initialize() {
    Robot.pneumaticStilts.extendRearRight();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}