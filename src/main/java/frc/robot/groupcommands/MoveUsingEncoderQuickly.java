package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.commands.MoveUsingEncoderPID;
import frc.robot.telemetries.Trace;

public class MoveUsingEncoderQuickly extends CommandGroup {

    public MoveUsingEncoderQuickly(double distanceInInches) {
        addSequential(new MoveUsingEncoderPID(distanceInInches));
    }

    public void initialize() {
        Trace.getInstance().logCommandStart("Move Quickly");
        DrivetrainEncoderPIDController.getInstance().pidMultiton.setPIDTerms(DrivetrainEncoderPIDController.getInstance().getPForMovingQuickly(),
        DrivetrainEncoderPIDController.getInstance().getIForMovingQuickly(),
        DrivetrainEncoderPIDController.getInstance().getDForMovingQuickly());
        DrivetrainEncoderPIDController.getInstance().pidMultiton.setTolerance(DrivetrainEncoderPIDController.getInstance().getToleranceForMovingQuickly());
    }


}