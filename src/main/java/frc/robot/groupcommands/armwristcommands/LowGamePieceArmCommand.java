package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class LowGamePieceArmCommand extends Command{
  public LowGamePieceArmCommand() {
  }

  @Override
  protected void initialize() {
    super.initialize();
    System.out.println("Creating Low Game Piece command");
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(),
        Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle + wristAngle) > 0;
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    System.out.println("Still Creating Low Game Piece Command");
    if (DriveClawMotorsSafely.hasBall) {
      (new RocketShipLowCargo(positiveWrist, sameSidePlacement, shoulderAngle)).start();;
    } else {
      System.out.println("creating low hatch command");
      (new LowHatch(positiveWrist, sameSidePlacement, shoulderAngle)).start();;
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}