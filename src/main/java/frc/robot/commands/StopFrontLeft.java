package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopFrontLeft extends Command {

  public void initialize() {
    Robot.pneumaticStilts.stopFrontLeft();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}