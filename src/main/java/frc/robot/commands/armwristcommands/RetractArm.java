package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.IncrementalPidSetpoint;
import frc.robot.telemetries.Trace;

public class RetractArm extends Command {

  private static final double SAFESETPOINTDELTA = 1;
  private static boolean overrideAndFinishCommand = false;

  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam) {
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  private IncrementalPidSetpoint incrementalPidSetpoint;

  public RetractArm(double inchesExtension) {

    requires(Robot.extendableArmAndWrist);
    System.out.println("Retracting Arm...");
    incrementalPidSetpoint = new IncrementalPidSetpoint(inchesExtension, SAFESETPOINTDELTA,
        Robot.getCurrentArmPosition()::getArmRetraction);
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("RetractArm");
    overrideAndFinishCommand = false;
    System.out.println("Running the Arm to: " + incrementalPidSetpoint.getFinalAngleSetpoint());

  }

  protected void execute() {
    ExtendableArmPIDController.getInstance().setSetpoint(incrementalPidSetpoint.getSetpoint());
  }

  @Override
  protected void interrupted() {
    end();
  }

  protected void end() {
    Trace.getInstance().logCommandStop("RetractArm");

  }

  @Override
  protected boolean isFinished() {

    return overrideAndFinishCommand
        || (incrementalPidSetpoint.isSetpointFinalSetpoint() && ExtendableArmPIDController.getInstance().onTarget());
  }

}