package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;

public class RotateWrist extends Command {

  private double encTicks;

  public RotateWrist(double angle) {
    this.encTicks = angle / MoveArmAndWristSafely.WRISTTICKSTODEGREES;
    requires(Robot.extendableArmAndWrist);
  }

  protected void execute() {
    WristPIDController.getInstance().setSetpoint(encTicks);
    WristPIDController.getInstance().enable();
  }

  @Override
  protected void interrupted() {
    end();
  }

  protected void end() {
    WristPIDController.getInstance().disable();
  }

  @Override
  protected boolean isFinished() {
    return !WristPIDController.getInstance().isEnabled();
  }

}