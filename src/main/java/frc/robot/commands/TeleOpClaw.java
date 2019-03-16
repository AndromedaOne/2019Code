package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.telemetries.Trace;
import frc.robot.utilities.EnumeratedRawAxis;
import frc.robot.utilities.POVDirectionNames;

public class TeleOpClaw extends Command {

  public TeleOpClaw() {
    requires(Robot.claw);
    System.out.println("Initializing Teleop Claw Control...");
  }

  protected void initialize() {
    Trace.getInstance().logCommandStart("TeleOpClaw");
  }

  protected void execute() {
    double rightTriggerValue = EnumeratedRawAxis.RIGHTTRIGGER.getRawAxis(Robot.operatorController);
    double leftTriggerValue = EnumeratedRawAxis.LEFTTRIGGER.getRawAxis(Robot.operatorController);

    boolean leftPOVPressed = POVDirectionNames.getPOVWest(Robot.operatorController);
    boolean rightPOVPressed = POVDirectionNames.getPOVEast(Robot.operatorController);

    // Threshold Code
    if (rightTriggerValue < 0.2) {
      rightTriggerValue = 0;
    }
    if (leftTriggerValue < 0.2) {
      leftTriggerValue = 0;
    }

    if (rightTriggerValue == 0 && leftTriggerValue == 0) {
      DriveClawMotorsSafely.DriveClawMotors(0);
    } else if (rightTriggerValue > leftTriggerValue) {
      DriveClawMotorsSafely.DriveClawMotors(1);
    } else {
      DriveClawMotorsSafely.DriveClawMotors(-1);
    }

    if (leftPOVPressed) {
      Robot.claw.openClaw();
    } else if (rightPOVPressed) {
      Robot.claw.closeClaw();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.claw.driveIntakeMotors(0);
    Trace.getInstance().logCommandStop("TeleOpClaw");
  }

  protected void interupt() {
    end();
  }
}