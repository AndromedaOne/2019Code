package frc.robot.groupcommands.autopaths;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.*;

public class LoadingStationFromBay3 extends CommandGroup {
  /**
   * Takes the Robot from Bay 3 to the loading station.
   */
  public LoadingStationFromBay3() {
    // TODO: Add non placeholder values.
    addSequential(new MoveUsingEncoderPID(-10));
    addSequential(new TurnToCompassHeading(0));
    addSequential(new MoveUsingEncoderPID(-15));
    if (AutoStartingConfig.onRightSide) {
      addSequential(new TurnToCompassHeading(270));
    } else {
      addSequential(new TurnToCompassHeading(90));
    }
    addSequential(new MoveUsingEncoderPID(-10));
    addSequential(new TurnToCompassHeading(0));
    addSequential(new MoveUsingBackUltrasonic(17));
    addSequential(new MoveUsingBackLineFollower());
  }
}