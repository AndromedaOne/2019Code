package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;
import frc.robot.utilities.ButtonsEnumerated;

public class HighGamePieceArmCommand extends CommandGroup {
  public HighGamePieceArmCommand() {
    addSequential(new TuckArm());
    addSequential(new RotateShoulder(ArmPositions.HIGHROCKETGAMEPIECE));
    addSequential(new RotateWrist(ArmPositions.HIGHROCKETGAMEPIECE));
    addSequential(new RetractArm(ArmPositions.HIGHROCKETGAMEPIECE));
  }

  @Override
  protected void initialize() {
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(),
        Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle + wristAngle) > 0;
    double directionFactor = positiveWrist ? 1 : -1;
    directionFactor = sameSidePlacement ? directionFactor*1 : directionFactor*-1;

    RotateShoulder.setShoulderDirectionFactor(directionFactor);
    RotateWrist.setWristDirectionFactor(directionFactor);
  }

}