package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.commands.MoveUsingEncoderPID;

public class MoveUsingEncoderPrecisely extends CommandGroup {

    public MoveUsingEncoderPrecisely(double distanceInInches) {
        addSequential(new MoveUsingEncoderPID(distanceInInches));
    }

    public void initialize() {
        DrivetrainEncoderPIDController.getInstance().pidMultiton.setPIDTerms(
            DrivetrainEncoderPIDController.getInstance().getPForMovingPrecisely(), 
            DrivetrainEncoderPIDController.getInstance().getIForMovingPrecisely(), 
            DrivetrainEncoderPIDController.getInstance().getDForMovingPrecisely());
        DrivetrainEncoderPIDController.getInstance().pidMultiton.setTolerance(
            DrivetrainEncoderPIDController.getInstance().getToleranceForMovingPrecisely()
        );
    }


}