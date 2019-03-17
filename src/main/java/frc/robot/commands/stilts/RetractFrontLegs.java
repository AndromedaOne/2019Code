package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.POVDirectionNames;

public class RetractFrontLegs extends Command {

  public void initialize() {
    Trace.getInstance().logCommandStart("RetractFrontLegs");
    System.out.println("Retracting Front Legs");
    Robot.pneumaticStilts.retractFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    Trace.getInstance().logCommandStop("RetractFrontLegs");
    return !POVDirectionNames.getPOVEast(Robot.driveController);
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("RetractFrontLegs");
  }
}