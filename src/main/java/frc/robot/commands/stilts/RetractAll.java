package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.POVDirectionNames;

public class RetractAll extends Command {

  public void initialize() {
    Trace.getInstance().logCommandStart("RetractAll");
    System.out.println("Retracting All Legs");
    Robot.pneumaticStilts.retractFrontLegs();
    Robot.pneumaticStilts.retractRearLegs();
  }

  @Override
  protected boolean isFinished() {
    Trace.getInstance().logCommandStop("RetractAll");
    System.out.println("Done Retracting All Legs");
    return !POVDirectionNames.getPOVSouth(Robot.driveController);
  }

}