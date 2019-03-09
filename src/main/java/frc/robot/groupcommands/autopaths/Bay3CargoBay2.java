package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;
import frc.robot.groupcommands.armwristcommands.*;

public class Bay3CargoBay2 extends CommandGroup {
  /**
   * Left and Right group command for moving the Robot from Level 1 to Bay 3 with
   * a Hatch Panel preloaded, then back to the loading station to pick up a cargo,
   * then to Bay 2, where it will deposit it in the cargo bay.
   */
  public Bay3CargoBay2() {
    // TODO: Add real values.
    addSequential(new Bay3FromHab());
    // Moves to Bay 3 and deposits the game piece.
    addSequential(new MoveUsingEncoderPID(0));
    // Moves back.
    addSequential(new TurnToCompassHeading(0));
    // Turns north so we can go backwards.
    addSequential(new MoveUsingEncoderPID(0));
    // Moves backwards.
    addParallel(new LowOppositeSideGamePieceArmCommand());
    /*
     * While we move backwards, we swap the position of the robot arm so that it is
     * on the back side of the robot.
     */
    addSequential(new TurnToFieldCenter());
    // Turns to the center of the field.
    addSequential(new MoveUsingEncoderPID(0));
    // Moves.
    addSequential(new TurnToCompassHeading(0));
    // Turns north.
    addSequential(new MoveUsingEncoderPID(0));
    // Move backwards, as we are still facing the back.
    addParallel(new OpenClaw());
    // While we move, we open the claw in preparation for grabbing the cargo.
    addSequential(new CloseClaw());
    // Grab cargo.
    addSequential(new MoveUsingEncoderPID(0));
    // Move forward.
    addParallel(new MiddleGamePieceArmCommand());
    // Swap sides while we move.
    addSequential(new TurnToFieldOutside());
    // Turn to outside.
    addSequential(new MoveUsingEncoderPID(0));
    // Move.
    addSequential(new TurnToCompassHeading(0));
    // Turn to North.
    addSequential(new MoveUsingEncoderPID(0));
    // Move to be around Bay 2.
    addSequential(new TurnToFieldCenter());
    // Turn to center.
    addSequential(new MoveUsingEncoderPID(0));
    // Move slightly to be around the tape.
    addSequential(new MoveUsingFrontLineFollower());
    // Follow the line
    addSequential(new OpenClaw());
    // Drop the cargo in the cargo Bay.
    // This is the end of the program.
  }

}