package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.POVDirectionNames;

public class RetractRearLegs extends Command {

  public void initialize() {
    Trace.getInstance().logCommandStart("RetractRearLegs");
    System.out.println("Retracting Rear Legs");
    Robot.pneumaticStilts.retractRearLegs();
  }

  @Override
  protected boolean isFinished() {
    Trace.getInstance().logCommandStop("RetractRearLegs");
    return !POVDirectionNames.getPOVWest(Robot.driveController);
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("RetractRearLegs");
  }
}