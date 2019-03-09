package frc.robot.groupcommands.stilts;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.stilts.RaiseAll;
import frc.robot.commands.stilts.RetractFrontLegs;
import frc.robot.commands.stilts.RetractRearLegs;
import frc.robot.Robot;
import frc.robot.commands.IntakeArmControl;
import frc.robot.commands.IntakeArmControl.MoveIntakeArmDirection;
import frc.robot.commands.L3RollIntakeIn;

public class L3Climb extends CommandGroup {
 
    public L3Climb() {
        addSequential(new RaiseAll());
        //moving intake twice to ensure that it's at ground height
        addSequential(new IntakeArmControl(MoveIntakeArmDirection.DOWN));
        addSequential(new IntakeArmControl(MoveIntakeArmDirection.DOWN));
        addSequential(new RetractFrontLegs());
        addSequential(new L3RollIntakeIn());
        //moving intake twice to ensure that it's in stowed position
        addSequential(new IntakeArmControl(MoveIntakeArmDirection.UP));
        addSequential(new IntakeArmControl(MoveIntakeArmDirection.UP));
        addSequential(new RetractRearLegs());
    }
}