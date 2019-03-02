package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class MiddleGamePieceArmCommand extends Command {
  public MiddleGamePieceArmCommand() {
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(),
        Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle + wristAngle) > 0;

    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (DriveClawMotorsSafely.hasBall) {
      (new MiddleCargo(positiveWrist, sameSidePlacement, shoulderAngle)).start();
    } else {
      (new MiddleHatch(positiveWrist, sameSidePlacement, shoulderAngle)).start();
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}