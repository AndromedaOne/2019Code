package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RaiseAllLegs extends CommandGroup {
    public RaiseAllLegs() {
        addParallel(new RaiseFrontLeft());
        addParallel(new RaiseFrontRight());
        addParallel(new RaiseRearLeft());
        addParallel(new RaiseRearRight());
    }
}