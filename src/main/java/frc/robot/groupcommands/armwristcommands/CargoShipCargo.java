package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.*;

public class CargoShipCargo extends CommandGroup {
  public CargoShipCargo() {
    addSequential(new RotateWrist(-98.59));
    addSequential(new ExtendArm(20.92));
    addSequential(new RotateShoulder(151));
  }
}