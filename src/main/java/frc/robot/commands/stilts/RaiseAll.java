package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RaiseAll extends Command {

  public void initialize() {
    System.out.println("Raising all legs");
    Robot.pneumaticStilts.extendFrontLegs();
    Robot.pneumaticStilts.extendRearLegs();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}