package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.Command;
/**
 * When this is selected, it automatically runs Bay3FromHab().
 */
public class Bay3FromHabScheduler extends Command {

    protected void initialize() {
        (new Bay3FromHab()).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}