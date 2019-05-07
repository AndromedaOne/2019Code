package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.subsystems.drivetrain.*;

public class DoubleMoveDrivetrainWithEncoder extends CommandGroup {
  public DoubleMoveDrivetrainWithEncoder(double distanceInInches) {
    double setpoint = distanceInInches + Robot.drivetrainLeftRearEncoder.getDistanceTicks()
        / DrivetrainEncoderPIDController.getInstance().TICKSTOINCHESRATIO;
    addSequential(new MoveUsingEncoderQuickly(setpoint));
    addSequential(new MoveUsingEncoderPrecisely(setpoint));
  }

  public void initialize() {
    Robot.driveTrain.setGear(DriveTrain.RobotGear.LOWGEAR);
  }
}