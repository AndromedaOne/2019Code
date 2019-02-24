/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.intake.IntakeArmPositionsEnum;
import frc.robot.utilities.EnumeratedRawAxis;

public class TeleopIntake extends Command {
  private double mod = 0.9;
  public TeleopIntake() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.intake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Joystick opController = Robot.operatorController;

    double upDownValue = opController.getRawAxis(EnumeratedRawAxis.RIGHTSTICKHORIZONTAL.getValue());

    /**
     * negative is moving to stow
     * positive is moving to deploy
     */
    if (upDownValue > 0) {
      if (Robot.intake.isAtLimitSwitch()) {
        Robot.intake.moveIntakeArm(0);
        Robot.intake.setCurrentIntakeArmPosition(IntakeArmPositionsEnum.STOWED);
      } else {
        Robot.intake.moveIntakeArm(upDownValue * mod);
        Robot.intake.setCurrentIntakeArmPosition(IntakeArmPositionsEnum.UNKNOWN);
      }
    } else {
      if (Robot.intake.isAtGround()) {
        Robot.intake.setCurrentIntakeArmPosition(IntakeArmPositionsEnum.GROUNDHEIGHT);
        Robot.intake.moveIntakeArm(0);
      } else {
        Robot.intake.moveIntakeArm(upDownValue * mod);
        Robot.intake.setCurrentIntakeArmPosition(IntakeArmPositionsEnum.UNKNOWN);
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.intake.moveIntakeArm(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
