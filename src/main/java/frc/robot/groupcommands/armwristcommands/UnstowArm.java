package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class UnstowArm extends CommandGroup {

  public UnstowArm() {
    double armRetractAmount = 0;
    double shoulderRotateAmount = Robot.robotHasBall() ? 0 : 1;
    double wristRotateAmount = Robot.robotHasBall() ? 2 : 3;
    // do stuff
    addSequential(new RetractArm(armRetractAmount));
    addParallel(new RotateShoulder(shoulderRotateAmount));
    addSequential(new RotateWrist(wristRotateAmount));
  }
}