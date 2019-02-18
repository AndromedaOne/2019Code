package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class HighGamePieceArmCommand extends CommandGroup {
  public HighGamePieceArmCommand() {
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.armRotateEncoder1.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(), Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle +  wristAngle) > 0;

    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (DriveClawMotorsSafely.hasBall) {
      addSequential(new HighCargo(positiveWrist, sameSidePlacement, shoulderAngle));
    } else {
      addSequential(new HighHatch(positiveWrist, sameSidePlacement, shoulderAngle));
    }
  }
}