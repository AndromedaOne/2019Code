package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.ButtonsEnumerated;

public class RaiseBackLegs extends Command {

  public RaiseBackLegs() {
    requires(Robot.pneumaticStilts);
  }

  public void initialize() {
    Trace.getInstance().logCommandStart("RaiseBackLegs");
    System.out.println("Raising back legs");
    Robot.pneumaticStilts.extendRearLegs();
  }

  @Override
  protected boolean isFinished() {
    Trace.getInstance().logCommandStop("RaiseBackLegs");
    return !ButtonsEnumerated.isPressed(ButtonsEnumerated.BACKBUTTON, Robot.driveController);
  }

  @Override
  protected void end() {
    Robot.pneumaticStilts.stopRearLegs();
  }

  @Override
  protected void interrupted() {
    end();
  }

}