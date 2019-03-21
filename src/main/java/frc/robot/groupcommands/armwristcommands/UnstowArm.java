package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class UnstowArm extends CommandGroup {

  public UnstowArm() {
    double armRetractAmount = 0;
    double shoulderRotateAmount = DriveClawMotorsSafely.hasBall ? 0 : 1;
    double wristRotateAmount = DriveClawMotorsSafely.hasBall ? 2 : 3;
    // do stuff
    addSequential(new RetractArm(armRetractAmount));
    addParallel(new RotateShoulder(shoulderRotateAmount));
    addSequential(new RotateWrist(wristRotateAmount));
  }
}