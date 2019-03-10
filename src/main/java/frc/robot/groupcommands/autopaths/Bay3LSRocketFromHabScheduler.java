package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.Command;
/**
 * When this is selected, it automatically runs Bay3Bay2FromHab().
 */
public class Bay3LSRocketFromHabScheduler extends Command {

    protected void initialize() {
        (new Bay3LSRocketFromHab()).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}