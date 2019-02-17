package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CloseClaw extends Command {

  protected void initialize() {
    requires(Robot.claw);
  }

  protected void execute() {
    Robot.claw.closeClaw();

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  protected void end() {

  }

  protected void interupt() {

  }

}