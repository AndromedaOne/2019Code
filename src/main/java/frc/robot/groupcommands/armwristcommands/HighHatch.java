package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class HighHatch extends CommandGroup {
  public HighHatch(boolean positiveWrist, boolean sameSidePlacement) {
    double directionFactor = positiveWrist ? -1 : 1;
    if (sameSidePlacement) {
      addSequential(new RotateWrist(89.39 * directionFactor));
      addSequential(new ExtendArm(0));
      addSequential(new RotateShoulder(-126.16 * directionFactor));
    } else {
      addSequential(new ExtendArm(20));
      addSequential(new RotateWrist(89.39 * -directionFactor));
      addSequential(new RotateShoulder(-126.16 * -directionFactor));

      addSequential(new RotateWrist(89.39 * -directionFactor));
      addSequential(new ExtendArm(0));
      addSequential(new RotateShoulder(-126.16 * -directionFactor));
    }
  }
}