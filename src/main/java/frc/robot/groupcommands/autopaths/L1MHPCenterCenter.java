package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;
import frc.robot.Robot;

public class L1MHPCenterCenter extends CommandGroup {
    private boolean isDone = false;

    public L1MHPCenterCenter(boolean turnRight) {

    }

    @Override
    protected boolean isFinished() {
        return isDone;
    }

}