package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;

public class RetractArm extends Command {

  private double inchesExtension;

  private static boolean overrideAndFinishCommand = false;

  public static void setOverrideAndFinishCommand(boolean overrideAndFinishCommandParam) {
    overrideAndFinishCommand = overrideAndFinishCommandParam;
  }

  public RetractArm(double inchesExtensionParam) {
    inchesExtension = inchesExtensionParam;
    requires(Robot.extendableArmAndWrist);
    System.out.println("Retracting Arm...");
  }

  @Override
  protected void initialize() {
    overrideAndFinishCommand = false;
    System.out.println("Running the Arm to: " + inchesExtension);
    ExtendableArmPIDController.getInstance().setSetpoint(inchesExtension);
    ExtendableArmPIDController.getInstance().enable();
  }

  protected void execute() {

  }

  @Override
  protected void interrupted() {
    end();
  }

  protected void end() {
    System.out.println("Done with the Retraction");
    // ExtendableArmPIDController.getInstance().disable();
  }

  @Override
  protected boolean isFinished() {
    return overrideAndFinishCommand || ExtendableArmPIDController.getInstance().onTarget();
  }

}