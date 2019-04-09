package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.IntakeArmControl;
import frc.robot.commands.IntakeArmControl.MoveIntakeArmDirection;

public class DoubleMoveIntake extends CommandGroup {
  public DoubleMoveIntake(MoveIntakeArmDirection directionToMove) {

    addSequential(new IntakeArmControl(directionToMove));
    addSequential(new IntakeArmControl(directionToMove));
  }
}