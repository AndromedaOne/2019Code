package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.utilities.ButtonsEnumerated;

public class BButtonArmCommand extends CommandGroup {
  public BButtonArmCommand() {
    boolean positiveShoulder = Robot.armRotateEncoder1.getDistanceTicks() > 0;
    boolean sameSidePlacement = ButtonsEnumerated.isPressed(ButtonsEnumerated.LEFTBUMPERBUTTON,
        Robot.operatorController);
    if (Robot.driveClawMotorsSafely.hasBall) {
      addSequential(new CargoShipCargo(positiveShoulder, sameSidePlacement));
    } else {
      addSequential(new LoadingStation(positiveShoulder, sameSidePlacement));
    }
  }
}