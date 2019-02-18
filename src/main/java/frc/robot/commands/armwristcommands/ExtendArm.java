package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;

public class ExtendArm extends Command {

  private double inchesExtension;

  public ExtendArm(double inchesExtensionParam) {
    inchesExtension = inchesExtensionParam;
    requires(Robot.extendableArmAndWrist);
  }
  @Override
  protected void initialize() {
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
    ExtendableArmPIDController.getInstance().disable();
  }

  @Override
  protected boolean isFinished() {
    return !ExtendableArmPIDController.getInstance().isEnabled() || ExtendableArmPIDController.getInstance().onTarget();
  }

}