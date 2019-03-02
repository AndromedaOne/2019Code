package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;

public class RaiseFrontLegs extends Command {

  public RaiseFrontLegs() {
    requires(Robot.pneumaticStilts);
  }

  public void initialize() {
    System.out.println("Raising front legs");
    Robot.pneumaticStilts.extendFrontLegs();
  }

  @Override
  protected boolean isFinished() {
    return !ButtonsEnumerated.isPressed(ButtonsEnumerated.STARTBUTTON, Robot.driveController);
  }

  @Override
  protected void end() {
    Robot.pneumaticStilts.stopFrontLegs();
  }
  @Override
  protected void interrupted() {
    end();
  }
}