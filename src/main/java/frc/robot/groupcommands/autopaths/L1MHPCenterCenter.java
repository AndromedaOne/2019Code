package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;
import frc.robot.groupcommands.armwristcommands.LowGamePieceArmCommand;
import frc.robot.groupcommands.armwristcommands.LowSameSideGamePieceArmCommand;


public class L1MHPCenterCenter extends CommandGroup {
    private boolean isDone = false;

    public L1MHPCenterCenter(boolean onRightSide) {
        // Move forward to Center Bay Wall, while we move the arm into placing position
        addSequential(new MoveUsingFrontUltrasonic(2));
        addParallel(new LowSameSideGamePieceArmCommand());
        // This releases the hatch, If we are having trouble lining up we can add
        // A line follow sequential here
        addSequential(new CloseClaw());
        // Move away from the wall
        addSequential(new MoveUsingFrontUltrasonic(15));

        if(onRightSide) {
            // Turns us East to the right loading station
            addSequential(new TurnToCompassHeading(90));
        } else {
            // Turns us West to the left loading station
            addSequential(new TurnToCompassHeading(270));
        }

        addSequential(new MoveUsingEncoderPID(20));
        // Turn towards the loading station
        addSequential(new TurnToCompassHeading(0));
        // Drive up to the loading station
        addSequential(new MoveUsingFrontUltrasonic(18));

    }

    @Override
    protected boolean isFinished() {
        return isDone;
    }

}