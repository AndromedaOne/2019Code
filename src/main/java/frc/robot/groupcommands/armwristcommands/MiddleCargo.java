package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class MiddleCargo extends CommandGroup {
  public MiddleCargo(boolean positiveShoulder, boolean sameSidePlacement) {
    double directionFactor = positiveShoulder ? -1 : 1;
    if (sameSidePlacement) {
      addSequential(new RotateWrist(82.95 * directionFactor));
      addSequential(new ExtendArm(18.9));
      addSequential(new RotateShoulder(-175.52 * directionFactor));
    } else {
      addSequential(new ExtendArm(20));
      addSequential(new RotateWrist(89.39 * -directionFactor));
      addSequential(new RotateShoulder(-175.52 * -directionFactor));

      addSequential(new RotateWrist(82.95 * -directionFactor));
      addSequential(new ExtendArm(18.9));
      addSequential(new RotateShoulder(-175.52 * -directionFactor));
    }
  }
}