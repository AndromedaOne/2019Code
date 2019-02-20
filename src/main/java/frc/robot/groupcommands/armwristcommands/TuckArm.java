package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateWrist;

public class TuckArm extends CommandGroup {
  private final double centerOfRobotWristTuckNoGoZone = 20;
  private final double safeExtensionInCenterOfRobotNoGoZone = MoveArmAndWristSafely.maxExtensionInches-0.5;
  private final double safeExtensionOfArm = 22;

  public TuckArm(double shoulderAngle, boolean wristPositiveDirection) {
    if (Math.abs(shoulderAngle) <= centerOfRobotWristTuckNoGoZone) {
      addSequential(new RetractArm(safeExtensionInCenterOfRobotNoGoZone));
      if (wristPositiveDirection) {
        addSequential(new RotateWrist(100));
      } else {
        addSequential(new RotateWrist(-100));
      }
      addSequential(new RetractArm(safeExtensionOfArm));
    } else {
      addSequential(new RetractArm(safeExtensionOfArm));
      if (wristPositiveDirection) {
        addSequential(new RotateWrist(90));
      } else {
        addSequential(new RotateWrist(-90));
      }
    }
  }

}