package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class LowGamePieceArmCommand extends CommandGroup {
  public LowGamePieceArmCommand() {
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(),
        Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle + wristAngle) > 0;
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (DriveClawMotorsSafely.hasBall) {
      addSequential(new RocketShipLowCargo(positiveWrist, sameSidePlacement, shoulderAngle));
    } else {
      addSequential(new LowHatch(positiveWrist, sameSidePlacement, shoulderAngle));
    }
  }
}