package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.ExtendableArmPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.ShoulderPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.WristPIDController;
import frc.robot.subsystems.extendablearmandwrist.ExtendableArmAndWrist;
import frc.robot.utilities.EnumeratedRawAxis;

public class TeleopArm extends Command {

  Joystick armController = Robot.armController;
  ExtendableArmAndWrist extendableArmAndWrist = Robot.extendableArmAndWrist;
  final double sinPi4 = Math.sin(Math.PI / 4);
  final double cosPi4 = Math.cos(Math.PI / 4);
  private final double WRISTSTICKVALUETODEGREES = 1;
  private final double SHOULDERSTICKVALUETODEGREES = 1;
  private final double EXTENSIONSTICKVALUETOINCHES = 1;

  public TeleopArm() {
    requires(Robot.extendableArmAndWrist);
  }

  @Override
  protected void execute() {
    double extensionValue = EnumeratedRawAxis.LEFTSTICKHORIZONTAL.getRawAxis(armController);
    double wristRotateValue = EnumeratedRawAxis.RIGHTSTICKVERTICAL.getRawAxis(armController);

    wristRotateValue = -wristRotateValue * 0.5;
    extensionValue = -extensionValue;
    double shoulderRotateValue = -EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(armController);
    if (Math.abs(shoulderRotateValue) < 0.01) {
      shoulderRotateValue = 0.0;
    }
    double topArmEncoderTicks = Robot.topArmExtensionEncoder.getDistanceTicks();
    double bottomArmEncoderTicks = Robot.bottomArmExtensionEncoder.getDistanceTicks();

    double currentWristPosition = MoveArmAndWristSafely.getWristRotDegrees(topArmEncoderTicks, bottomArmEncoderTicks);
    double currentExtensionPosition = MoveArmAndWristSafely.getExtensionIn(topArmEncoderTicks, bottomArmEncoderTicks);
    double currentShoulderPosition = MoveArmAndWristSafely.getShoulderRotDeg(Robot.shoulderEncoder.getDistanceTicks());

    double wristSetpoint = currentWristPosition + wristRotateValue*WRISTSTICKVALUETODEGREES;
    if(wristSetpoint > MoveArmAndWristSafely.maxWristRotDegrees) {
      wristSetpoint = MoveArmAndWristSafely.maxWristRotDegrees;
    }
    if(wristSetpoint < -MoveArmAndWristSafely.maxWristRotDegrees) {
      wristSetpoint = -MoveArmAndWristSafely.maxWristRotDegrees;
    }

    double shoulderSetpoint = currentShoulderPosition + shoulderRotateValue*SHOULDERSTICKVALUETODEGREES;
    if(shoulderSetpoint > MoveArmAndWristSafely.maxShoulderRotDegrees) {
      shoulderSetpoint = MoveArmAndWristSafely.maxShoulderRotDegrees;
    }
    if(shoulderSetpoint < -MoveArmAndWristSafely.maxShoulderRotDegrees) {
      shoulderSetpoint = -MoveArmAndWristSafely.maxShoulderRotDegrees;
    }

    double extensionSetpoint = currentExtensionPosition + extensionValue*EXTENSIONSTICKVALUETOINCHES;
    if(extensionSetpoint > MoveArmAndWristSafely.maxShoulderRotDegrees) {
      extensionSetpoint = MoveArmAndWristSafely.maxShoulderRotDegrees;
    }
    if(extensionSetpoint < 0) {
      extensionSetpoint = 0;
    }

    ExtendableArmPIDController.getInstance().setSetpoint(extensionSetpoint);
    WristPIDController.getInstance().setSetpoint(wristSetpoint);
    ShoulderPIDController.getInstance().setSetpoint(shoulderSetpoint);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void interrupted() {
    end();
  }

  @Override
  protected void end() {
    // try {
    MoveArmAndWristSafely.stop();
    // } catch (ArmOutOfBoundsException e) {
    // System.out.println(e.getMessage());
    // }
  }

}