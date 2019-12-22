package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainRearUltrasonicPIDController;

public class MoveUsingFrontUltrasonic extends Command {

  private DrivetrainRearUltrasonicPIDController frontUltrasonic = DrivetrainRearUltrasonicPIDController.getInstance();
  private double setpoint = 0;

  /**
   * Takes a Setpoint in inches This is the distance you will be from the wall
   */
  public MoveUsingFrontUltrasonic(int distanceFromWallInInches) {
    setpoint = distanceFromWallInInches;
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
