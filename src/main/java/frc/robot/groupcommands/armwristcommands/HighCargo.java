package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.*;

public class HighCargo extends CommandGroup {
  private final double shoulderPosition = 126.16;
  private final double wristPosition = -56.58;
  private final double extensionPosition = 0;

  public HighCargo(boolean positiveWristCurently, boolean sameSidePlacement, double shoulderAngle) {
    double directionFactor = positiveWristCurently ? -1 : 1;
    boolean positiveWristDestination = (wristPosition * directionFactor) > 0 ? true : false;

    addSequential(new TuckArm(shoulderAngle, positiveWristDestination));
    if (sameSidePlacement) {
      addSequential(new RotateShoulder(-shoulderPosition * directionFactor));
      addSequential(new RotateWrist(wristPosition * directionFactor));
      addSequential(new RetractArm(extensionPosition));
      
    } else {
      addSequential(new RotateShoulder(-shoulderPosition * directionFactor));
      addSequential(new RotateWrist(wristPosition * directionFactor));
      addSequential(new RetractArm(extensionPosition));
    }
  }
}