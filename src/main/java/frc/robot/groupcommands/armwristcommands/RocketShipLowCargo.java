package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class RocketShipLowCargo extends CommandGroup {

  public RocketShipLowCargo(boolean positiveShoulder, boolean sameSidePlacement) {
    double directionFactor = positiveShoulder ? 1 : -1;

    if (sameSidePlacement) {
      addSequential(new RotateWrist(-91.12 * directionFactor));
      addSequential(new ExtendArm(25.6));
      addSequential(new RotateShoulder(10.73 * directionFactor));
    } else {
      addSequential(new ExtendArm(20));
      addSequential(new RotateWrist(-91.12 * -directionFactor));
      addSequential(new ExtendArm(25.6));
      addSequential(new RotateShoulder(10.73 * -directionFactor));
    }
  }

}