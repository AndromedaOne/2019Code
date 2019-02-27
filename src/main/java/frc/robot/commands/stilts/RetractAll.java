package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractAll extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractFrontLegs();
    Robot.pneumaticStilts.retractRearLegs();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}