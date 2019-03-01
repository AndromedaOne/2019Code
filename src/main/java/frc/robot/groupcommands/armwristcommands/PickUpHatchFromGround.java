package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;
import frc.robot.utilities.ButtonsEnumerated;

public class PickUpHatchFromGround extends CommandGroup {
  private final double shoulderPosition = 50;
  private final double wristPosition = -50;
  private final double extensionPosition = 28;

  public PickUpHatchFromGround() {
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(),
        Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle + wristAngle) > 0;
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    double directionFactor = positiveWrist ? 1 : -1;
    boolean positiveWristDestination = (-wristPosition * directionFactor) > 0 ? true : false;
    positiveWristDestination = sameSidePlacement ? positiveWristDestination : !positiveWristDestination;

    addSequential(new TuckArm(shoulderAngle, positiveWristDestination));
    if (sameSidePlacement) {
      addSequential(new RotateShoulder(shoulderPosition * directionFactor));
      addSequential(new RotateWrist(wristPosition * directionFactor));
      addSequential(new RetractArm(extensionPosition));
    }
  }

}