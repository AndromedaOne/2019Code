package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class LowHatch extends CommandGroup {

  public LowHatch() {
    addSequential(new RotateWrist(-95.5));
    addSequential(new RotateShoulder(4));
    addSequential(new ExtendArm(11.88));
  }
}