package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class RocketShipLowCargo extends CommandGroup {

  private final double shoulderPosition = -10.73;
  private final double wristPosition = 91.12;
  private final double extensionPosition = 25.6;

  public RocketShipLowCargo(boolean positiveWristCurently, boolean sameSidePlacement, double shoulderAngle) {
    double directionFactor = positiveWristCurently ? 1 : -1;
    boolean positiveWristDestination = (-wristPosition * directionFactor) > 0 ? true : false;
    positiveWristDestination = sameSidePlacement ? positiveWristDestination : !positiveWristDestination;

    addSequential(new TuckArm(shoulderAngle, positiveWristDestination));

    if (sameSidePlacement) {
      addSequential(new RotateShoulder(shoulderPosition * directionFactor));
      addSequential(new RotateWrist(wristPosition * directionFactor));
      addSequential(new RetractArm(extensionPosition));
    } else {
      addSequential(new RotateShoulder(-shoulderPosition * directionFactor));
      addSequential(new RotateWrist(-wristPosition * directionFactor));
      addSequential(new RetractArm(extensionPosition));
    }
  }

}