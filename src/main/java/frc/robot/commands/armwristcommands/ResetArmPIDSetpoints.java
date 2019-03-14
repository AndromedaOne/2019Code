package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.ArmPosition;
import frc.robot.Robot;

public class ResetArmPIDSetpoints extends Command {

  @Override
  protected void initialize() {
    super.initialize();
    RetractArm.setOverrideAndFinishCommand(true);
    RotateShoulder.setOverrideAndFinishCommand(true);
    RotateWrist.setOverrideAndFinishCommand(true);

    ArmPosition currentArmPosition = Robot.getCurrentArmPosition();
    Robot.extendableArmPIDController.setSetpoint(currentArmPosition.getArmRetraction());

    Robot.wristPIDController.setSetpoint(currentArmPosition.getWristAngle());

    Robot.shoulderPIDController.setSetpoint(currentArmPosition.getShoulderAngle());
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

}