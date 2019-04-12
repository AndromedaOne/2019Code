package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.pidcontrollers.IntakePIDController;
import frc.robot.commands.IntakeArmControl;
import frc.robot.commands.IntakeArmControl.MoveIntakeArmDirection;

public class MoveIntakePrecisely extends CommandGroup {

    public MoveIntakePrecisely(MoveIntakeArmDirection directionToMove) {
       addSequential(new IntakeArmControl(directionToMove));
    }

    public void initialize() {
        IntakePIDController.getInstance().pidMultiton.setTolerance(0.01);
        IntakePIDController.getInstance().pidMultiton.setPIDTerms(0.125, 0, 0.25);
        
    }

    public void end() {
        IntakePIDController.getInstance().pidMultiton.setTolerance(0.035);
        IntakePIDController.getInstance().pidMultiton.setPIDTerms(0.25, 0, 0.25);
        
    }

    public void interrupted() {
        end();
    }
}