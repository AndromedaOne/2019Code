package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;

public class YButtonArmCommand extends CommandGroup {
  public YButtonArmCommand() {
    boolean positiveShoulder = Robot.armRotateEncoder1.getDistanceTicks() > 0;
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (Robot.driveClawMotorsSafely.hasBall) {
      // high cargo
    } else {
      // high height
    }
  }
}