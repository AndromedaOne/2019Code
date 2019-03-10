package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.Command;
/**
 * When this is selected, it automatically runs Bay3RocketBay3().
 */
public class Bay3RocketBay3Scheduler extends Command {

    protected void initialize() {
        (new Bay3RocketBay3()).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}