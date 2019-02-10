package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class IntakeReady extends CommandGroup {

  public IntakeReady() {
    addSequential(new RotateWrist(-43.57));
    addSequential(new RotateShoulder(-10.67));
    addSequential(new ExtendArm(20.56));
  }
}