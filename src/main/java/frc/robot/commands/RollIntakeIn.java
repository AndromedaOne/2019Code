package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.ButtonsEnumerated;

public class RollIntakeIn extends Command {

  private double mod = -1;

  protected void initialize() {
    Trace.getInstance().logCommandStart("RollIntakeIn");
  }

  protected void execute() {
    Robot.intake.rollIntake(mod);
  }

  @Override
  protected boolean isFinished() {
    return !ButtonsEnumerated.getJoystickButton(OI.rollIntakeButton, Robot.operatorController).get();
  }

  protected void end() {
    Robot.intake.rollIntake(0);
    Trace.getInstance().logCommandStop("RollIntakeIn");

  }

  protected void interrupted() {
    end();
  }

}