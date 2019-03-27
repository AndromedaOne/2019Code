package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;
import frc.robot.telemetries.Trace;

public class RotateShoulder extends Command {

  private double encDegrees;
  ShoulderPIDController sPidController;

  private static boolean overrideAndFinishCommand = false;

  private double counter = 0;
  private boolean isFinished = false;

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

    isFinished = false;
    Trace.getInstance().logCommandStart("RotateShoulder");
    System.out.println("Running the Shoulder to: " + encDegrees);
    overrideAndFinishCommand = false;
    sPidController.setSetpoint(encDegrees);
    sPidController.enable();

    counter = 0;
  }

  protected void execute() {

    counter++;

    if(counter >= 50) {
      isFinished = true;
    }

  }

  protected void interrupted() {
    end();
  }

  protected void end() {
    Trace.getInstance().logCommandStop("RotateShoulder");

  }

  @Override
  protected boolean isFinished() {

    return overrideAndFinishCommand || sPidController.onTarget() || isFinished;
  }

}