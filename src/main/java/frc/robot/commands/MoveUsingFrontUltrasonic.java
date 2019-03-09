package frc.robot.commands;

import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainUltrasonicPIDController;

public class MoveUsingFrontUltrasonic extends MoveTurnBase {

  private DrivetrainUltrasonicPIDController frontUltrasonic = DrivetrainUltrasonicPIDController.getInstance();
  private double setpoint = 0;

  /**
   * Takes a Setpoint in inches This is the distance you will be from the wall
   */
  public MoveUsingFrontUltrasonic(int distanceFromWallInInches) {
    setpoint = distanceFromWallInInches;
  }

  public void initialize() {
    moveUsingFrontUltrasonic(setpoint);
  }
}
