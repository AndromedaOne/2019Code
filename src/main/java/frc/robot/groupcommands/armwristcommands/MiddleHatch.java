package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class MiddleHatch extends CommandGroup {
  public MiddleHatch(boolean positiveShoulder, boolean sameSidePlacement) {
    double directionFactor = positiveShoulder ? -1 : 1;
    if (sameSidePlacement) {
      addSequential(new RotateWrist(57 * directionFactor));
      addSequential(new ExtendArm(25.234));
      addSequential(new RotateShoulder(-147 * directionFactor));
    } else {
      addSequential(new ExtendArm(20));
      addSequential(new RotateWrist(95 * -directionFactor));
      addSequential(new RotateShoulder(-147 * -directionFactor));

      addSequential(new RotateWrist(57 * -directionFactor));
      addSequential(new ExtendArm(25.234));
      addSequential(new RotateShoulder(-147* -directionFactor));
    }
  }
}