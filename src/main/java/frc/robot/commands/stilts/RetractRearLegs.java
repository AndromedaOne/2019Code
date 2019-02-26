package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RetractRearLegs extends Command {

  public void initialize() {
    Robot.pneumaticStilts.retractRearLeft();
    Robot.pneumaticStilts.retractRearRight();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}