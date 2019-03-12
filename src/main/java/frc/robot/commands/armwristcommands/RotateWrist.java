package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;
import frc.robot.telemetries.Trace;

public class RotateWrist extends Command {

  private double encDegrees;
  private static boolean overrideAndFinishCommand = false;

  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam) {
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RotateWrist(double angle) {
    encDegrees = angle;
    requires(Robot.extendableArmAndWrist);
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("RotateWrist");
    System.out.println("Running the wrist to: " + encDegrees);
    overrideAndFinishCommand = false;
    WristPIDController.getInstance().setSetpoint(encDegrees);
    WristPIDController.getInstance().enable();
  }

  protected void execute() {

  }

  @Override
  protected void interrupted() {
    end();
  }

  protected void end() {
    WristPIDController.getInstance().disable();
  }

  @Override
  protected boolean isFinished() {
    Trace.getInstance().logCommandStop("RotateWrist");
    return overrideAndFinishCommand || WristPIDController.getInstance().onTarget();
  }

}