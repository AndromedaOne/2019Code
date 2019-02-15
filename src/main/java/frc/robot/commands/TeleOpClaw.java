package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.utilities.EnumeratedRawAxis;

public class TeleOpClaw extends Command {

  protected void initialize() {
    requires(Robot.claw);
  }

  protected void execute() {
    double rightTriggerValue = EnumeratedRawAxis.RIGHTTRIGGER.getRawAxis(Robot.operatorController);
    double leftTriggerValue = EnumeratedRawAxis.LEFTTRIGGER.getRawAxis(Robot.operatorController);
    if (rightTriggerValue == 0 && leftTriggerValue == 0) {
      Robot.claw.driveIntakeMotors(0);
    } else if (rightTriggerValue > leftTriggerValue) {
      Robot.claw.driveIntakeMotors(rightTriggerValue);
    } else {
      Robot.claw.driveIntakeMotors(-leftTriggerValue);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.claw.driveIntakeMotors(0);
  }

  protected void interupt() {
    end();
  }
}