package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class IntakeReady extends CommandGroup {

  public IntakeReady(boolean positiveWrist) {

    if (!positiveWrist) {
      addSequential(new RetractArm(20.56));
      addSequential(new RotateWrist(-90));
      addSequential(new RotateShoulder(-10.67));
    }
    addSequential(new RotateWrist(-24.2));
    addSequential(new RetractArm(20.13));
    addSequential(new RotateShoulder(58.5));
  }
}