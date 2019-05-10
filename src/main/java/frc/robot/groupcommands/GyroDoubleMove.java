package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GyroDoubleMove extends CommandGroup {
  public GyroDoubleMove(double desiredAngle, boolean useCompassHeading) {
    addSequential(new TurnToDeltaAngleQuickly(desiredAngle, useCompassHeading));
    addSequential(new TurnToDeltaAnglePrecisely(desiredAngle, useCompassHeading));

  }

}