package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class CargoShipAndLoadingCommand extends Command {

  @Override
  protected void initialize() {
    super.initialize();
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(),
        Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle + wristAngle) > 0;

    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (DriveClawMotorsSafely.hasBall) {
      (new CargoShipCargo(positiveWrist, sameSidePlacement, shoulderAngle)).start();

    } else {
      (new LoadingStation(positiveWrist, sameSidePlacement, shoulderAngle)).start();
    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}