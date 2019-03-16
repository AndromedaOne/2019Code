package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class OpenClaw extends Command {

  public OpenClaw() {
    requires(Robot.claw);
    System.out.println("Opening claw...");
  }

  protected void initialize() {

  }

  protected void execute() {
    Robot.claw.openClaw();
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  protected void end() {

  }

  protected void interupt() {

  }

}