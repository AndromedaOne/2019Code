package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class MiddleGamePieceArmCommand extends CommandGroup {
  public MiddleGamePieceArmCommand() {
    boolean positiveShoulder = Robot.armRotateEncoder1.getDistanceTicks() > 0;
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (DriveClawMotorsSafely.hasBall) {
      addSequential(new MiddleCargo(positiveShoulder, sameSidePlacement));
    } else {
      addSequential(new MiddleHatch(positiveShoulder, sameSidePlacement));
    }
  }
}