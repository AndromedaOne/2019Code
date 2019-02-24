package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.Command;

public class RollIntakeGroupCommandScheduler extends Command {

    @Override
    protected void initialize() {
        super.initialize();
        (new RollIntakeGroupCommand()).start();
    }
    @Override
    protected boolean isFinished() {
        return true;
    }

}