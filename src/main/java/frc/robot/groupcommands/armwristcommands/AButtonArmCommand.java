package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;

public class AButtonArmCommand extends CommandGroup {
  public AButtonArmCommand() {
    boolean positiveShoulder = Robot.armRotateEncoder1.getDistanceTicks() > 0;
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (Robot.driveClawMotorsSafely.hasBall) {
      addSequential(new RocketShipLowCargo(positiveShoulder, sameSidePlacement));
    } else {
      addSequential(new LowHatch(positiveShoulder, sameSidePlacement));
    }
  }
}