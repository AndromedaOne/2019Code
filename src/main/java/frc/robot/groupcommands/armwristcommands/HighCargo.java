package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.*;

public class HighCargo extends CommandGroup {
  public HighCargo(boolean positiveWrist, boolean sameSidePlacement) {
    double directionFactor = positiveWrist ? -1 : 1;
    if (sameSidePlacement) {
      addSequential(new RotateWrist(56.58 * directionFactor));
      addSequential(new ExtendArm(0));
      addSequential(new RotateShoulder(-126.16 * directionFactor));
    } else {
      addSequential(new ExtendArm(20));
      addSequential(new RotateWrist(95 * -directionFactor));
      addSequential(new RotateShoulder(-126.16 * -directionFactor));

      addSequential(new RotateWrist(56.58 * -directionFactor));
      addSequential(new ExtendArm(0));
      addSequential(new RotateShoulder(-126.16 * -directionFactor));
    }
  }
}