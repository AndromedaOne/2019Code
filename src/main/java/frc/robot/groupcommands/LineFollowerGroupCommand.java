package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.MoveUsingFrontLineFollower;
import frc.robot.commands.TurnToDeltaAngle;

/**
 * The GroupCommand to follow the lines on the arena floor.
 */

public class LineFollowerGroupCommand extends CommandGroup {

  public LineFollowerGroupCommand(double deltaAngle) {
    double deltaAngleDegrees = deltaAngle * (180 / Math.PI);
    addSequential(new TurnToDeltaAngle(deltaAngleDegrees));
    addSequential(new MoveUsingFrontLineFollower());

  }
}