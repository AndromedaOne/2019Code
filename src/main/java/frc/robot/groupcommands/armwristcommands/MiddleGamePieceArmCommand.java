package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;
import frc.robot.utilities.ButtonsEnumerated;

public class MiddleGamePieceArmCommand extends CommandGroup {
  public MiddleGamePieceArmCommand() {
    addSequential(new TuckArm());
    addSequential(new RotateShoulder(ArmPositions.MIDDLEROCKETGAMEPIECE));
    addSequential(new RotateWrist(ArmPositions.MIDDLEROCKETGAMEPIECE));
    addSequential(new RetractArm(ArmPositions.MIDDLEROCKETGAMEPIECE));
  }

  @Override
  protected void initialize() {
    super.initialize();
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(),
        Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle + wristAngle) > 0;
    double directionFactor = positiveWrist ? 1 : -1;
    boolean positiveWristDestination = (-wristAngle * directionFactor) > 0 ? true : false;
    positiveWristDestination = sameSidePlacement ? positiveWristDestination : !positiveWristDestination;

    RotateShoulder.setShoulderDirectionFactor(directionFactor);
    RotateWrist.setWristDirectionFactor(directionFactor);
  }
}