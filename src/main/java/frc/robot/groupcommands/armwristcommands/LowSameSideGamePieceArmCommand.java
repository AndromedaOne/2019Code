package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class LowSameSideGamePieceArmCommand extends CommandGroup {
  public LowSameSideGamePieceArmCommand() {
    System.out.println("Creating Low Game Piece command");
    double shoulderAngle = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
    double wristAngle = MoveArmAndWristSafely.getWristRotDegrees(Robot.topArmExtensionEncoder.getDistanceTicks(),
        Robot.bottomArmExtensionEncoder.getDistanceTicks());
    boolean positiveWrist = (shoulderAngle + wristAngle) > 0;
    
    System.out.println("Still Creating Low Game Piece Command");
    if (DriveClawMotorsSafely.hasBall) {
      addSequential(new RocketShipLowCargo(positiveWrist, sameSidePlacement, shoulderAngle));
    } else {
      System.out.println("creating low hatch command");
      addSequential(new LowHatch(positiveWrist, sameSidePlacement, shoulderAngle));
    }
  }

  @Override
  protected void initialize() {
    super.initialize();
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    setSameSidePlacement(sameSidePlacement);
  }
}