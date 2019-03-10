package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;
import frc.robot.groupcommands.armwristcommands.*;

public class Bay3LSRocketFromHab extends CommandGroup {
  /**
   * This group command takes the Robot from the Hab zone to Bay 3, and from Bay
   * 3, it moves back to the Loading Station to pick up an unspecified game piece
   * and moves to the side closest to the driver station on the rocket. There it's
   * up to the driver to decide which level the robot should raise its arm to, but
   * for default purposes, it will always raise to the lowest hatch port.
   */
  public Bay3LSRocketFromHab() {
    /*
     * TODO: add real values and determine what we're doing at the loading station,
     * because we need to know what's getting written for the rocket.
     */

    addSequential(new Bay3FromHab());
    // From the Hab platform, we move to Bay 3.
    addSequential(new MoveUsingEncoderPID(0));
    // Move backwards.
    addSequential(new TurnToCompassHeading(180));
    // Turn South.
    addSequential(new MoveUsingEncoderPID(0));
    // Move South.
    if (AutoStartingConfig.onRightSide) {
      addSequential(new TurnToCompassHeading(270));
    } else {
      addSequential(new TurnToCompassHeading(90));
    }
    // Turn to face the center field direction.
    addSequential(new MoveUsingEncoderPID(0));
    // Move to avoid the rocket and be inline with the loading station.
    addSequential(new TurnToCompassHeading(180));
    // Turn South
    addSequential(new MoveUsingEncoderPID(0));
    // Move to the loading station.
    /*
     * Here we need to add what we do at the loading station.
     */
    addSequential(new MoveUsingEncoderPID(0));
    // Move back.
    if (AutoStartingConfig.onRightSide) {
      addSequential(new TurnToCompassHeading(270));
    } else {
      addSequential(new TurnToCompassHeading(90));
    }
    // Turn towards the rocket.
    addSequential(new MoveUsingEncoderPID(0));
    // Move to the line.
    addSequential(new MoveUsingFrontLineFollower());
    // Follows the line.
    /* Here we need to figure out what we're doing again. */
  }
}