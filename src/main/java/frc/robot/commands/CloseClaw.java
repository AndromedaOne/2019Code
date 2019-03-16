package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;

public class CloseClaw extends Command {

  protected void initialize() {
    Trace.getInstance().logCommandStart("CloseClaw");
    requires(Robot.claw);
    System.out.println("Closing claw...");
  }

  protected void execute() {
    Robot.claw.closeClaw();

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Trace.getInstance().logCommandStop("CloseClaw");

  }

  protected void interupt() {

  }

}