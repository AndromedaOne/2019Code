package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class MoveUsingBackUltrasonic extends Command {

  /**
   * Takes a Setpoint in inches This is the distance you will be from the wall
   */
  public MoveUsingBackUltrasonic(int distanceFromWallInInches) {

  }

  public void initialize() {
    // TODO There is no back ultrasonic set up yet
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
