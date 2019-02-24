package frc.robot.groupcommands.autopaths;
import frc.robot.groupcommands.armwristcommands.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Bay3Bay2FromHab extends CommandGroup {
    /**
     * Left and Right Level 1 Hatch Pannel Autonomous placement for Bays 2 and 3 of the 
     * respective side of the platform. Calling isLevel1RightSide as true tells the robot that 
     * it is currently on the right side of the level 1 platform, calling false does the 
     * opposite. A Hatch Panel should be pre-loaded onto the robot.
     */
    public Bay3Bay2FromHab(boolean onRightSide, boolean onLevelTwo) {

            addSequential(new Bay3FromHab());

            addSequential(new LoadingStationFromBay3());

            //  - Logic for placing hatch on Bay 2 from Loading Station -
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
