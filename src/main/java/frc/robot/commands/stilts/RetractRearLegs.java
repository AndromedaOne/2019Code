package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.POVDirectionNames;

public class RetractRearLegs extends Command {

  public void initialize() {
    System.out.println("Retracting Rear Legs");
    Robot.pneumaticStilts.retractRearLegs();
  }

  @Override
  protected boolean isFinished() {
    return !POVDirectionNames.getPOVWest(Robot.driveController);
  }
}