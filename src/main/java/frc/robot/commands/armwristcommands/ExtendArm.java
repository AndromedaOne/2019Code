package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ArmPIDController;

public class ExtendArm extends Command {

  private double encTicks;

  public ExtendArm(double encTicks) {
    this.encTicks = encTicks;
    requires(Robot.extendableArmAndWrist);
  }

  protected void execute() {
    ArmPIDController.getInstance().setSetpoint(encTicks);
    ArmPIDController.getInstance().enable();
  }

  @Override
  protected void interrupted() {
    end();
  }

  protected void end() {
    ArmPIDController.getInstance().disable();
  }

  @Override
  protected boolean isFinished() {
    return !ArmPIDController.getInstance().isEnabled();
  }

}