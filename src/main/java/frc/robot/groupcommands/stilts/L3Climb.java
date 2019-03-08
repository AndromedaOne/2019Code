package frc.robot.groupcommands.stilts;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.stilts.RaiseAll;

public class L3Climb extends CommandGroup {

    public L3Climb() {
        addSequential(new RaiseAll());
        
    }
}