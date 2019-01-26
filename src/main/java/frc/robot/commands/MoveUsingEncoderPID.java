package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.DrivetrainEncoderPIDController;

public class MoveUsingEncoderPID extends Command {

  private DrivetrainEncoderPIDController drivetrainEncoder = DrivetrainEncoderPIDController.getInstance();

  private int _setpoint = 0;

  public MoveUsingEncoderPID(int setpoint) {
    _setpoint = setpoint;
  }

  public void initialize() {
    System.out.println(" -- Called! -- ");
    drivetrainEncoder.setSetpoint(_setpoint);
    drivetrainEncoder.enable();
  }

  public void execute() {
  }

  public boolean isFinished() {
    return drivetrainEncoder.isDone();
  }

  public void end() {
    drivetrainEncoder.stop();
  }

  public void interrupt() {
    drivetrainEncoder.stop();
  }
}
