package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class L3RollIntakeIn extends Command {

  private double mod = -1;

  protected void initialize() {

  }

  protected void execute() {
    Robot.intake.rollIntake(mod);
  }

  @Override
  protected boolean isFinished() {
    if (Robot.rearDownwardUltrasonic.getDistanceInches() < 2) {
      return true;
    } else {
      return false;
    }
  }

  protected void end() {
    Robot.intake.rollIntake(0);
  }

  protected void interrupted() {
    end();
  }

}