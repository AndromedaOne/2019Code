package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.POVDirectionNames;

public class RaiseAll extends Command {
  public static boolean isExtended = false;

  public void initialize() {
    Trace.getInstance().logCommandStart("RaiseAll");
    System.out.println("Raising all legs");
    Robot.pneumaticStilts.extendFrontLegs();
    Robot.pneumaticStilts.extendRearLegs();
    isExtended = true;
  }

  @Override
  protected boolean isFinished() {
    return !POVDirectionNames.getPOVNorth(Robot.driveController);
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("RaiseAll");
  }

}