package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TeleopIntake extends Command {

  public TeleopIntake() {
    requires(Robot.intake);
    System.out.println("Initializing Teleop Intake Arm Control...");

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}