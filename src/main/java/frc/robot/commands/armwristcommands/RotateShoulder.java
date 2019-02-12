package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;

public class RotateShoulder extends Command {

  private double encTicks;
  ShoulderPIDController sPidController;

  public RotateShoulder(double angle) {
    requires(Robot.extendableArmAndWrist);
    this.encTicks = angle/MoveArmAndWristSafely.SHOULDERTICKSTODEGRESS;
    sPidController = ShoulderPIDController.getInstance();
  }

  protected void execute() {
    sPidController.setSetpoint(encTicks);
    sPidController.enable();
  }

  protected void interrupted() {
    end();
  }

  protected void end() {
    sPidController.disable();
  }

  @Override
  protected boolean isFinished() {
    return !sPidController.isEnabled();
  }

}