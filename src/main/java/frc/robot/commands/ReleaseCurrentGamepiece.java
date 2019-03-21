package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ReleaseCurrentGamepiece extends Command {

  public ReleaseCurrentGamepiece() {
    requires(Robot.claw);
  }

  public void initialize() {

  }

  public void execute() {
    if (Robot.robotHasBall()) {
      Robot.intake.rollIntake(-0.75);
    }

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}