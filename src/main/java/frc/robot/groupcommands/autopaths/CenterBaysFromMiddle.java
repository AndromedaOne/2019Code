package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;
import frc.robot.groupcommands.armwristcommands.LowOppositeSideGamePieceArmCommand;
import frc.robot.groupcommands.armwristcommands.LowSameSideGamePieceArmCommand;


public class CenterBaysFromMiddle extends CommandGroup {

    public CenterBaysFromMiddle(boolean onRightSide) {
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
        // Drive up to the loading station 1 inch over the line
        addSequential(new MoveUsingBackUltrasonic(17));
        addParallel(new LowOppositeSideGamePieceArmCommand());
        // Uses line follower to align to the loading station
        addSequential(new MoveUsingBackLineFollower());
        addSequential(new OpenClaw());
        // Drive forward away from the wall
        addSequential(new MoveUsingEncoderPID(10));
        addParallel(new LowOppositeSideGamePieceArmCommand());

        // Turn to drive closer to the center bays
        if(onRightSide) {
            addSequential(new TurnToCompassHeading(270));
        } else {
            addSequential(new TurnToCompassHeading(90));
        }
        
        // Move to align with the center bay that hasn't been hatched™ yet - Devin
        addSequential(new MoveUsingEncoderPID(25));
        addSequential(new TurnToCompassHeading(0));
        // Drive over the tape extending from the wall
        addSequential(new MoveUsingFrontUltrasonic(17));
        addSequential(new MoveUsingFrontLineFollower());
        // Place second hatch
        addSequential(new CloseClaw());
    }
}