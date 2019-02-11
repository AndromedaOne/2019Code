package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.DriveClawMotorsSafely;
import frc.robot.utilities.ButtonsEnumerated;

public class XButtonArmCommand extends CommandGroup {
  public XButtonArmCommand() {
    boolean positiveShoulder = Robot.armRotateEncoder1.getDistanceTicks() > 0;
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (DriveClawMotorsSafely.hasBall) {
      // middle cargo
    } else {
      // middle height
    }
  }
}