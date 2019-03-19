package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.IncrementalPidSetpoint;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;
import frc.robot.telemetries.Trace;

public class RotateShoulder extends Command {

  private static final double SAFESETPOINTDELTA = 1;
  ShoulderPIDController sPidController;
  private IncrementalPidSetpoint incrementalPidSetpoint;

  private static boolean overrideAndFinishCommand = false;

  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam) {
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RotateShoulder(double angle) {
    requires(Robot.extendableArmAndWrist);

    sPidController = ShoulderPIDController.getInstance();
    System.out.println("Rotating shoulder to " + angle);
    incrementalPidSetpoint = new IncrementalPidSetpoint(angle, SAFESETPOINTDELTA,
        Robot.getCurrentArmPosition()::getShoulderAngle);
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("RotateShoulder");
    System.out.println("Running the Shoulder to: " + incrementalPidSetpoint.getFinalAngleSetpoint());
    overrideAndFinishCommand = false;
  }

  protected void execute() {
    sPidController.setSetpoint(incrementalPidSetpoint.getSetpoint());
  }

  protected void interrupted() {
    end();
  }

  protected void end() {
    Trace.getInstance().logCommandStop("RotateShoulder");
  }

  @Override
  protected boolean isFinished() {
    return overrideAndFinishCommand || (incrementalPidSetpoint.isSetpointFinalSetpoint() && sPidController.onTarget());
  }

}