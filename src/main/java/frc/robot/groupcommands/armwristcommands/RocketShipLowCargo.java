package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class RocketShipLowCargo extends CommandGroup {

  public RocketShipLowCargo() {
    addSequential(new RotateWrist(-91.12));
    addSequential(new RotateShoulder(10.73));
    addSequential(new ExtendArm(25.6));
  }

}