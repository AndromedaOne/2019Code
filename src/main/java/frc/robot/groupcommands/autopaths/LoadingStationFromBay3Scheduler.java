package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.Command;
/**
 * When this is selected, it automatically runs LoadingStationFromBay3().
 */
public class LoadingStationFromBay3Scheduler extends Command {

    protected void initialize() {
        (new LoadingStationFromBay3()).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}