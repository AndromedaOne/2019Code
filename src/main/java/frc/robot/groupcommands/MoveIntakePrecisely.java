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
        IntakePIDController.getInstance().pidMultiton.setTolerance(IntakePIDController.getInstance().getToleranceForMovingPrecisely());
        IntakePIDController.getInstance().pidMultiton.setPIDTerms(IntakePIDController.getInstance().getPForMovingPrecisely(), IntakePIDController.getInstance().getIForMovingPrecisely(), IntakePIDController.getInstance().getDForMovingPrecisely());
        
    }

    public void end() {
        IntakePIDController.getInstance().pidMultiton.setTolerance(IntakePIDController.getInstance().getToleranceForMovingQuickly());
        IntakePIDController.getInstance().pidMultiton.setPIDTerms(IntakePIDController.getInstance().getPForMovingQuickly(), IntakePIDController.getInstance().getIForMovingQuickly(), IntakePIDController.getInstance().getDForMovingQuickly());
        
    }

    public void interrupted() {
        end();
    }
}