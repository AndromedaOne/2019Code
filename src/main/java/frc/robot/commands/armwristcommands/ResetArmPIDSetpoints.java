package frc.robot.commands.armwristcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;

public class ResetArmPIDSetpoints extends Command {

    @Override
    protected void initialize() {
        super.initialize();
        RetractArm.setOverrideAndFinishCommand(true);
        RotateShoulder.setOverrideAndFinishCommand(true);
        RotateWrist.setOverrideAndFinishCommand(true);

        double topArmEncoderTicks = Robot.topArmExtensionEncoder.getDistanceTicks();
        double bottomArmEncoderTicks = Robot.bottomArmExtensionEncoder.getDistanceTicks();
        double extendableArmCurrentPosition = MoveArmAndWristSafely.getExtensionIn(topArmEncoderTicks, bottomArmEncoderTicks);
        Robot.extendableArmPIDController.setSetpoint(extendableArmCurrentPosition);

        double wristCurrentPosition = MoveArmAndWristSafely.getWristRotDegrees(topArmEncoderTicks, bottomArmEncoderTicks);
        Robot.wristPIDController.setSetpoint(wristCurrentPosition);

        double shoulderCurrentPosition = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());
        Robot.shoulderPIDController.setSetpoint(shoulderCurrentPosition);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}