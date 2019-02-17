package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractAll extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractFrontLeft();
    Robot.pneumaticStilts.retractFrontRight();
    Robot.pneumaticStilts.retractRearLeft();
    Robot.pneumaticStilts.retractRearRight();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}