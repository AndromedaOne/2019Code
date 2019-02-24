package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;
import frc.robot.groupcommands.armwristcommands.LowSameSideGamePieceArmCommand;

public class Bay3FromHab extends CommandGroup {

    /**
     * This Command assumes you are starting from Hab Platform
     * This does not support us being in the middle
     */
    public Bay3FromHab() {
        addSequential(new MoveOffHab());
        // Moving up to the line
        addSequential(new MoveUsingEncoderPID(10));
        addParallel(new LowSameSideGamePieceArmCommand());
        addSequential(new TurnToFieldCenter());
        // Moves us a set distance from the wall
        // This corrects for any lateral error and gives the line sensor 
        // Amply room to work
        addSequential(new MoveUsingFrontUltrasonic(17));
        addSequential(new MoveUsingFrontLineFollower());
        addSequential(new ReleaseCurrentGamepiece());
        // moves us away from the wall after we place the game piece
        addSequential(new MoveUsingFrontUltrasonic(3));
    }

}