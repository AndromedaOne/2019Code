package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class HPBay3 extends CommandGroup {

    /**
     * This Command assumes you are starting from Hab Platform
     * This does not support us being in the middle
     */
    public HPBay3(boolean onRightSide, boolean onLevelTwo, boolean useLineSensorTurn) {

        // Initial Move forward, move further for lv 2 because we are further back
        if(onLevelTwo) {
            addSequential(new MoveUsingEncoderPID(18));
            // Fix rotational error after fall from L2
            addSequential(new TurnToCompassHeading(0));
            addSequential(new MoveUsingBackUltrasonic(28));
        } else {
            addSequential(new MoveUsingBackUltrasonic(28));
        }


    }

}