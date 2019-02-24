package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class Bay3FromHab extends CommandGroup {

    /**
     * This Command assumes you are starting from Hab Platform
     * This does not support us being in the middle
     */
    public Bay3FromHab() {
        addSequential(new MoveOffHab());

    }

}