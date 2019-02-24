package frc.robot.commands;

import frc.robot.groupcommands.autopaths.AutoStartingConfig;

public class MoveOffHab extends MoveTurnBase {

    /**
     * This command will do the necassary movement for moving off the Hab
     * the command will determine what we should do if we are on L1 or L2
     */
    public MoveOffHab() {
    }
    
    public void initialize() {
        if (AutoStartingConfig.onLevelTwo) {
            // Drive off the platform
            moveUsingEncoderPID(25);
            turnToCompassHeading(0);
        }
        // This should bring us to a relatively known location
        moveUsingBackUltrasonic(18);
    }

}