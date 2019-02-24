package frc.robot.commands;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;

public class MoveUsingEncoderPID extends Command {

  private DrivetrainEncoderPIDController drivetrainEncoder = DrivetrainEncoderPIDController.getInstance();
  private Config config = Robot.getConfig().getConfig("subsystems");
  private int ticksPerInch = config.getInt("driveTrain.ticksToInches");

  private int setpoint = 0;

  /**
   * Takes a Setpoint in inches
   */
  public MoveUsingEncoderPID(int distanceInInches) {
    setpoint = distanceInInches * ticksPerInch;
  }

  public void initialize() {
    System.out.println(" -- Called! -- ");
    drivetrainEncoder.setRelativeSetpoint(setpoint);
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
