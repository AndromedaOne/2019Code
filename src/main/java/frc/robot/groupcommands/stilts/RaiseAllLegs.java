package frc.robot.groupcommands.stilts;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.stilts.*;

public class RaiseAllLegs extends CommandGroup {
  public RaiseAllLegs() {
    addParallel(new RaiseFrontLeft());
    addParallel(new RaiseFrontRight());
    addParallel(new RaiseRearLeft());
    addParallel(new RaiseRearRight());
  }
}