package frc.robot.commands;

import com.typesafe.config.Config;

import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.telemetries.Trace;

public class MoveUsingEncoderPID extends MoveTurnBase {

  private Config config = Robot.getConfig().getConfig("subsystems");
  private double setpoint = 0;

  /**
   * Takes a Setpoint in inches
   */
  public MoveUsingEncoderPID(double distanceInInches) {
    setpoint = distanceInInches;
    System.out.println("Moving " + distanceInInches + " in inches using the encoders");
  }

  public void initialize() {
    Trace.getInstance().logCommandStart("MoveUsingEncoderPID");
    DrivetrainEncoderPIDController.getInstance().setSetpoint(setpoint);
    DrivetrainEncoderPIDController.getInstance().enable();
  }

  public boolean isFinished(){
    return DrivetrainEncoderPIDController.getInstance().onTarget();
  }
  public void end() {
    DrivetrainEncoderPIDController.getInstance().disable();
    DrivetrainEncoderPIDController.getInstance().reset();
  }
}
