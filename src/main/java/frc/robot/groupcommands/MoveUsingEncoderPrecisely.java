package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.commands.MoveUsingEncoderPID;
import frc.robot.telemetries.Trace;

public class MoveUsingEncoderPrecisely extends CommandGroup {

  public MoveUsingEncoderPrecisely(double distanceInInches) {
    addSequential(new MoveUsingEncoderPID(distanceInInches));
  }

  public void initialize() {
    Trace.getInstance().logCommandStart("Move Preciseley");
    DrivetrainEncoderPIDController.getInstance().pidMultiton.setPIDTerms(
        DrivetrainEncoderPIDController.getInstance().getPForMovingPrecisely(),
        DrivetrainEncoderPIDController.getInstance().getIForMovingPrecisely(),
        DrivetrainEncoderPIDController.getInstance().getDForMovingPrecisely());
    DrivetrainEncoderPIDController.getInstance().pidMultiton
        .setTolerance(DrivetrainEncoderPIDController.getInstance().getToleranceForMovingPrecisely());
    System.out.println("Hey ETHAN, here's the NUMBER YOU WANTED: "
        + DrivetrainEncoderPIDController.getInstance().getPForMovingPrecisely());
  }

  public void end() {

  }

}