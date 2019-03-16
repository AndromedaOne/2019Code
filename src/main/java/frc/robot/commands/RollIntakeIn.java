package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;

public class RollIntakeIn extends Command {

  private double mod = -1;

  protected void initialize() {
    System.out.println("Rolling intake...");
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

  }

  protected void interrupted() {
    end();
  }

}