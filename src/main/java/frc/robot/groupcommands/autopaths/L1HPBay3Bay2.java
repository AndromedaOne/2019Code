package frc.robot.groupcommands.autopaths;
import frc.robot.groupcommands.armwristcommands.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class L1HPBay3Bay2 extends CommandGroup {
    private boolean isDone = false;
    private boolean isLevel1RightSide;
    /**
     * Left and Right Level 1 Hatch Pannel Autonomous placement for Bays 2 and 3 of the 
     * respective side of the platform. Calling isLevel1RightSide as true tells the robot that 
     * it is currently on the right side of the level 1 platform, calling false does the 
     * opposite. A Hatch Panel should be pre-loaded onto the robot.
     */
    public L1HPBay3Bay2(boolean isLevel1RightSide) {
        //TODO: add values to these commands, I just put the arbitrary commands.
        if (isLevel1RightSide == false) {
            //Starting on the platform.
            addSequential(new MoveUsingEncoderPID(0));
            //Move to free ourselves of the platform.
            addSequential(new TurnToCompassHeading(0));
            //Turning.
            addSequential(new MoveUsingEncoderPID(0));
            //Moving a small amount, because of the position of the platform and the 
            //Rockets.
            addSequential(new TurnToCompassHeading(0));
            //Turning, we should be clear of obstacles.
            addSequential(new MoveUsingEncoderPID(0));
            //Driving to Bay 3.
            addParallel(new LowSameSideGamePieceArmCommand());
            //While we're driving, raise the arm to the specified level.
            addSequential(new TurnToCompassHeading(0));
            //Turning to be around the line.
            addSequential(new MoveUsingEncoderPID(0));
            //Moves so we're over the line.
            addSequential(new MoveUsingFrontLineFollower());
            //This is the command that follows the line to Bay 3.
            addSequential(new CloseClaw());
            /*Close the claw to release the Hatch Panel.
            At this point, we will return to the loading station and obtain the 
            Second Hatch Panel.
            */
            addSequential(new MoveUsingBackLineFollower());
            //Follow the line backwards.
            addSequential(new TurnToCompassHeading(0));
            //Turn to face the loading station.
            addSequential(new MoveUsingEncoderPID(0));
            //Move most of the way.
            addSequential(new TurnToCompassHeading(0));
            //Turn slightly because of the rocket.
            addSequential(new MoveUsingEncoderPID(0));
            //Move, we should be able to merely turn and pick up another hatch.
            addSequential(new TurnToCompassHeading(0));
            //Turn to face the loading station.
            addSequential(new MoveUsingEncoderPID(0));
            //Move to the loading station.
            addSequential(new OpenClaw());
            //Open the claw to grab a Hatch Panel.
            addSequential(new MoveUsingEncoderPID(0));
            //Move back.
            addSequential(new TurnToCompassHeading(0));
            //Turn.
            addSequential(new MoveUsingEncoderPID(0));
            //Avoid the rocket.
            addSequential(new TurnToCompassHeading(0));
            //Turn.
            addParallel(new LowOppositeSideGamePieceArmCommand());
            //Swap sides of the arm while we move.
            addSequential(new TurnToCompassHeading(0));
            //Turn.
            addSequential(new MoveUsingEncoderPID(0));
            //Move onto the line.
            addSequential(new MoveUsingBackLineFollower());
            //Move into Bay 2.
            addSequential(new CloseClaw());
            //Close the claw to release the Hatch Panel.
            addSequential(new MoveUsingFrontLineFollower());
            //Move to the end of the line.
            addSequential(new LowSameSideGamePieceArmCommand());
            //Move the arm to the front of the robot.
            //This is the end of the auto.
        }
        if (isLevel1RightSide == true) {
            //Starting on the platform.
            addSequential(new MoveUsingEncoderPID(0));
            //Move to free ourselves of the platform.
            addSequential(new TurnToCompassHeading(0));
            //Turning.
            addSequential(new MoveUsingEncoderPID(0));
            //Moving a small amount, because of the position of the platform and the 
            //Rockets.
            addSequential(new TurnToCompassHeading(0));
            //Turning, we should be clear of obstacles.
            addSequential(new MoveUsingEncoderPID(0));
            //Driving to Bay 3.
            addParallel(new LowSameSideGamePieceArmCommand());
            //While we're driving, raise the arm to the specified level.
            addSequential(new TurnToCompassHeading(0));
            //Turning to be around the line.
            addSequential(new MoveUsingEncoderPID(0));
            //Moves so we're over the line.
            addSequential(new MoveUsingFrontLineFollower());
            //This is the command that follows the line to Bay 3.
            addSequential(new CloseClaw());
            /*Close the claw to release the Hatch Panel.
            At this point, we will return to the loading station and obtain the 
            Second Hatch Panel.
            */
            addSequential(new MoveUsingBackLineFollower());
            //Follow the line backwards.
            addSequential(new TurnToCompassHeading(0));
            //Turn to face the loading station.
            addSequential(new MoveUsingEncoderPID(0));
            //Move most of the way.
            addSequential(new TurnToCompassHeading(0));
            //Turn slightly because of the rocket.
            addSequential(new MoveUsingEncoderPID(0));
            //Move, we should be able to merely turn and pick up another hatch.
            addSequential(new TurnToCompassHeading(0));
            //Turn to face the loading station.
            addSequential(new MoveUsingEncoderPID(0));
            //Move to the loading station.
            addSequential(new OpenClaw());
            //Open the claw to grab a Hatch Panel.
            addSequential(new MoveUsingEncoderPID(0));
            //Move back.
            addSequential(new TurnToCompassHeading(0));
            //Turn.
            addSequential(new MoveUsingEncoderPID(0));
            //Avoid the rocket.
            addSequential(new TurnToCompassHeading(0));
            //Turn.
            addParallel(new LowOppositeSideGamePieceArmCommand());
            //Swap sides of the arm while we move.
            addSequential(new TurnToCompassHeading(0));
            //Turn.
            addSequential(new MoveUsingEncoderPID(0));
            //Move onto the line.
            addSequential(new MoveUsingBackLineFollower());
            //Move into Bay 2.
            addSequential(new CloseClaw());
            //Close the claw to release the Hatch Panel.
            addSequential(new MoveUsingFrontLineFollower());
            //Move to the end of the line.
            addSequential(new LowSameSideGamePieceArmCommand());
            //Move the arm to the front of the robot.
            //This is the end of the auto.
        }
    }
}