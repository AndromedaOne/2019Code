package frc.robot.commands;

import com.typesafe.config.Config;

import frc.robot.Robot;
import frc.robot.telemetries.Trace;

public class MoveUsingEncoderPID extends MoveTurnBase {

  private Config config = Robot.getConfig().getConfig("subsystems");
  private int ticksPerInch = config.getInt("driveTrain.ticksToInches");
  private double setpoint = 0;

  /**
   * Takes a Setpoint in inches
   */
  public MoveUsingEncoderPID(int distanceInInches) {
    setpoint = distanceInInches * ticksPerInch;
    System.out.println("Moving " + distanceInInches + " in inches using the encoders");
  }

  public void initialize() {
    Trace.getInstance().logCommandStart("MoveUsingEncoderPID");
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
    Trace.getInstance().logCommandStop("MoveUsingEncoderPID");
    drivetrainEncoder.disable();
  }

  public void interrupt() {
    drivetrainEncoder.disable();
  }
}
