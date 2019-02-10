package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class MoveArmToOtherSideOfRobot extends CommandGroup {
  public MoveArmToOtherSideOfRobot(boolean positiveSideOfRobot, double shoulderDegrees) {
    double directionFactor = positiveSideOfRobot ? 1.0 : -1.0;

    addSequential(new ExtendArm(20));
    addSequential(new RotateWrist(95 * -directionFactor));
    addSequential(new RotateShoulder(shoulderDegrees));
  }
}