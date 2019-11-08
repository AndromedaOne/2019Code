/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;

public class DriveBackward extends Command {
  public DriveBackward() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_countExecute = 0;
    
    System.out.println("Kjell");
    Trace.getInstance().logCommandStart("DriveBackward");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.driveTrain.move(-0.5, 0, false);
    m_countExecute = m_countExecute + 1;
  }

  private int m_countExecute = 0;

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if (m_countExecute >= 15) {
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.driveTrain.stop();
    Trace.getInstance().logCommandStop("DriveBackward");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
