package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class LoadingStationFromBay3 extends CommandGroup {
    /**
     * Takes the Robot from Bay 3 to the loading station.
     * The Boolean specifies whether the Robot is on the right or left.
     * @param onRightSide
     */
    public LoadingStationFromBay3(boolean onRightSide) {
        //TODO: Add non placeholder values.
        addSequential(new MoveUsingEncoderPID(0));
        addSequential(new TurnToCompassHeading(180));
        addSequential(new MoveUsingEncoderPID(0));
        addSequential(new TurnToFieldCenter());
        addSequential(new MoveUsingEncoderPID(0));
        addSequential(new TurnToCompassHeading(180));
        addSequential(new MoveUsingEncoderPID(0));
    }
}