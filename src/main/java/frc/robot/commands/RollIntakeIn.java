package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RollIntakeIn extends Command {

  private double mod = 0.5;

  protected void initialize() {

  }

  protected void execute() {
    Robot.intake.rollIntake(-0.75 * mod);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.intake.rollIntake(0);

  }

  protected void interrupted() {
    end();
  }

}