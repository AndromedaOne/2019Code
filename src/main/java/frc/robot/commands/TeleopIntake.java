package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;

public class TeleopIntake extends Command {

  public TeleopIntake() {
    requires(Robot.intake);
    System.out.println("Initializing Teleop Intake Arm Control...");

  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("TeleopIntake");
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Trace.getInstance().logCommandStop("TeleopIntake");
  }

}