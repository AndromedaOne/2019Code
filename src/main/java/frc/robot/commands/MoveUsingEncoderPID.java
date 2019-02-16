package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;

public class MoveUsingEncoderPID extends Command {

  private DrivetrainEncoderPIDController drivetrainEncoder = DrivetrainEncoderPIDController.getInstance();

  private int _setpoint = 0;

  public MoveUsingEncoderPID(int setpoint) {
    _setpoint = setpoint;
  }

  public void initialize() {
    System.out.println(" -- Called! -- ");
    drivetrainEncoder.setRelativeSetpoint(_setpoint);
    drivetrainEncoder.enable();
  }

  public void execute() {
  }

  public boolean isFinished() {
    return drivetrainEncoder.onTarget();
  }

  public void end() {
    drivetrainEncoder.disable();
  }

  public void interrupt() {
    drivetrainEncoder.disable();
  }
}
