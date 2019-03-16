package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;
import frc.robot.telemetries.Trace;

public class RotateShoulder extends Command {

  private double encDegrees;
  ShoulderPIDController sPidController;

  private static boolean overrideAndFinishCommand = false;

  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam) {
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RotateShoulder(double angle) {
    requires(Robot.extendableArmAndWrist);
    encDegrees = angle;
    sPidController = ShoulderPIDController.getInstance();
    System.out.println("Rotating shoulder to " + angle);
  }

  @Override
  protected void initialize() {
    Trace.getInstance().logCommandStart("RotateShoulder");
    System.out.println("Running the Shoulder to: " + encDegrees);
    overrideAndFinishCommand = false;
    sPidController.setSetpoint(encDegrees);
    sPidController.enable();
  }

  protected void execute() {

  }

  protected void interrupted() {
    end();
  }

  protected void end() {
    System.out.println("Done with the Shoulder");
    sPidController.disable();
  }

  @Override
  protected boolean isFinished() {
    Trace.getInstance().logCommandStop("RotateShoulder");
    return overrideAndFinishCommand || sPidController.onTarget();
  }

}