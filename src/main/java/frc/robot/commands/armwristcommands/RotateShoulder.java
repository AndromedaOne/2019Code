package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;

public class RotateShoulder extends Command {

  private double encDegrees;
  ShoulderPIDController sPidController;

  public RotateShoulder(double angle) {
    requires(Robot.extendableArmAndWrist);
    encDegrees = angle;
    sPidController = ShoulderPIDController.getInstance();
  }

  @Override
  protected void initialize() {
    sPidController.setSetpoint(encDegrees);
    sPidController.enable();
  }

  protected void execute() {

  }

  protected void interrupted() {
    end();
  }

  protected void end() {
    sPidController.disable();
  }

  @Override
  protected boolean isFinished() {
    return !sPidController.isEnabled() || sPidController.onTarget();
  }

}