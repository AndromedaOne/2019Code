package frc.robot.commands;

import com.typesafe.config.Config;

import frc.robot.Robot;

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
    moveUsingEncoderPID(setpoint);
  }
}
