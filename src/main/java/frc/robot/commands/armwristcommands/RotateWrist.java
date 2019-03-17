package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;

public class RotateWrist extends Command {

  private double encDegrees;
  private static boolean overrideAndFinishCommand = false;

  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam) {
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RotateWrist(double angle) {
    encDegrees = angle;
    requires(Robot.extendableArmAndWrist);
    System.out.println("Rotating wrist to " + angle);
  }

  @Override
  protected void initialize() {
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
    System.out.println("Done with the wrist");
    //WristPIDController.getInstance().disable();
  }

  @Override
  protected boolean isFinished() {
    return overrideAndFinishCommand || WristPIDController.getInstance().onTarget();
  }

}