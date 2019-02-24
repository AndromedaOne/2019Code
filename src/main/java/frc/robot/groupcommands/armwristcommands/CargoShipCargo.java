package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.*;

public class CargoShipCargo extends CommandGroup {
  private final double shoulderPosition = 151;
  private final double wristPosition = -98.59;
  private final double extensionPosition = 20.92;

  public CargoShipCargo(boolean positiveWristCurently, boolean sameSidePlacement, double shoulderAngle) {
    double directionFactor = positiveWristCurently ? 1 : -1;
    boolean positiveWristDestination = (-95.5 * directionFactor) > 0 ? true : false;
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