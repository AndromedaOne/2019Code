package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.Command;
/**
 * When this is selected, it automatically runs Bay3Bay2FromHab().
 */
public class Bay3Bay2FromHabScheduler extends Command {

    protected void initialize() {
        (new Bay3Bay2FromHab()).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}