package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class Bay3FromHab extends CommandGroup {

  /**
   * This Command assumes you are starting from Hab Platform This does not support
   * us being in the middle
   */
  public Bay3FromHab() {
    if (AutoStartingConfig.onLevelTwo) {
      // Drive off the platform
      addSequential(new MoveUsingEncoderPID(25));
      addSequential(new TurnToCompassHeading(0));
    }
    // This should bring us to a relatively known location
    addSequential(new MoveUsingBackUltrasonic(48));
    // Moving up to the line
    addSequential(new MoveUsingEncoderPID(150));
    // addParallel(new LowSameSideGamePieceArmCommand());
    if (AutoStartingConfig.onRightSide) {
      addSequential(new TurnToCompassHeading(270));
    } else {
      addSequential(new TurnToCompassHeading(90));
    }
    // This gives us different paths depending on if we have a line follower
    if (AutoStartingConfig.hasLineFollower) {
      // Moves us a set distance from the wall
      // This corrects for any lateral error and gives the line sensor
      // Amply room to work
      addSequential(new MoveUsingFrontUltrasonic(17));
      addSequential(new MoveUsingFrontLineFollower());
    } else {
      // Moves us up to the wall
      addSequential(new MoveUsingBackUltrasonic(3));
    }
    addSequential(new ReleaseCurrentGamepiece());
    // moves us away from the wall after we place the game piece
    addSequential(new MoveUsingFrontUltrasonic(3));
  }

}