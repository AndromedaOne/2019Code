package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExtendLegs extends CommandGroup {

    public ExtendLegs() {
        System.out.println(" - Called ExtendLegs Constructor - ");
        addParallel(new RaiseFrontLeft());
        addParallel(new RaiseFrontRight());
        addParallel(new RaiseRearLeft());
        addParallel(new RaiseRearRight());
    }

}