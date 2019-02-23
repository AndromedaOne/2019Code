package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.commands.armwristcommands.ArmPositionValues;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateWrist;

public class TuckArm extends CommandGroup {
  private final double centerOfRobotWristTuckNoGoZone = 20;
  private final double safeExtensionInCenterOfRobotNoGoZone = MoveArmAndWristSafely.maxExtensionInches - 0.5;
  private final double safeExtensionOfArm = 22;

  public TuckArm() {
    addSequential(new RetractArm(ArmPositions.STOWEDFORROTATINGARM));
    addSequential(new RotateWrist(ArmPositions.STOWEDFORROTATINGARM)); 
    addSequential(new RetractArm(ArmPositions.STOWEDFORROTATINGARM));
    
  }

  @Override
  protected void initialize() {
    super.initialize();
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    if (Math.abs(shoulderAngle) <= centerOfRobotWristTuckNoGoZone) {
      RetractArm.setWristTuckExtensionToHigh(true);
    }
  }

}