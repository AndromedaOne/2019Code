/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveTrainPIDTest extends Command {
  private int counter;
  private double currentSpeed;
  private double speedIncrement;
  private final double kMaxSpeed = 1;

  public DriveTrainPIDTest() {
    requires(Robot.driveTrain);
    counter = 0;
    currentSpeed = .1;
    speedIncrement = .1;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    counter++;
    if (counter == 25) {
      currentSpeed = currentSpeed + speedIncrement;
      counter = 0;
    }
    if (currentSpeed > kMaxSpeed) {
      currentSpeed = kMaxSpeed;
      speedIncrement = -speedIncrement;
    }
    if (currentSpeed < -kMaxSpeed) {
      currentSpeed = -kMaxSpeed;
      speedIncrement = -speedIncrement;
    }
    Robot.driveTrain.move(currentSpeed, 0, false);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.move(0, 0, false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
