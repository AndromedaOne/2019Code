package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.*;

public class CargoShipCargo extends CommandGroup {
  public CargoShipCargo(boolean positiveWrist, boolean sameSidePlacement) {
    double directionFactor = positiveWrist ? 1 : -1;

    if (sameSidePlacement) {
      addSequential(new RotateWrist(-98.59 * directionFactor));
      addSequential(new ExtendArm(20.92));
      addSequential(new RotateShoulder(151 * directionFactor));
    } else {
      addSequential(new ExtendArm(20.92));
      addSequential(new RotateWrist(-98.59 * -directionFactor));
      addSequential(new ExtendArm(20.92));
      addSequential(new RotateShoulder(151 * -directionFactor));
    }
  }
}