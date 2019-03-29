package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.IncrementalPidSetpoint;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;
import frc.robot.telemetries.Trace;

public class RotateWrist extends Command {

  private static final double SAFESETPOINTDELTA = 1;
  private static boolean overrideAndFinishCommand = false;
  private int counter = 0;
  private boolean isFinished = false;
  private IncrementalPidSetpoint incrementalPidSetpoint;

  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam) {
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RotateWrist(double angle) {
    requires(Robot.extendableArmAndWrist);
    System.out.println("Rotating wrist to " + angle);
    incrementalPidSetpoint = new IncrementalPidSetpoint(angle, SAFESETPOINTDELTA,
        Robot.getCurrentArmPosition()::getWristAngle);
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("RotateWrist");
    counter = 0;
    isFinished = false;
    System.out.println("Running the wrist to: " + incrementalPidSetpoint.getFinalAngleSetpoint());
    overrideAndFinishCommand = false;
    WristPIDController.getInstance().setSetpoint(incrementalPidSetpoint.getSetpoint());

  }

  protected void execute() {
    
    counter++;
    if (counter > 50) {
      isFinished = true;
    }
  }

  @Override
  protected void interrupted() {
    end();
  }

  protected void end() {
    Trace.getInstance().logCommandStop("RotateWrist");

  }

  @Override
  protected boolean isFinished() {

    return overrideAndFinishCommand
        || (incrementalPidSetpoint.isSetpointFinalSetpoint() && WristPIDController.getInstance().onTarget())
        || isFinished;
  }

}