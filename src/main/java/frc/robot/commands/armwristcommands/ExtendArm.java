package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;

public class ExtendArm extends Command {

  private double encTicks;

  public ExtendArm(double encTicks) {
    this.encTicks = encTicks;
    requires(Robot.extendableArmAndWrist);
  }

  protected void execute() {
    ExtendableArmPIDController.getInstance().setSetpoint(encTicks);
    ExtendableArmPIDController.getInstance().enable();
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
    return !ExtendableArmPIDController.getInstance().isEnabled();
  }

}