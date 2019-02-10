package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class LoadingStation extends CommandGroup {

  public LoadingStation() {
    addSequential(new RotateWrist(122.11));
    addSequential(new RotateShoulder(11.75));
    addSequential(new ExtendArm(26.75));
  }
}