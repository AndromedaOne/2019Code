package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.extendablearmandwrist.ExtendableArmAndWrist;
import frc.robot.utilities.EnumeratedRawAxis;

public class TeleopArm extends Command {

  Joystick armController = Robot.armController;
  ExtendableArmAndWrist extendableArmAndWrist = Robot.extendableArmAndWrist;
  final double sinPi4 = Math.sin(Math.PI / 4);
  final double cosPi4 = Math.cos(Math.PI / 4);

  public TeleopArm() {
    requires(Robot.extendableArmAndWrist);
  }

  @Override
  protected void execute() {
    double armWristValue = EnumeratedRawAxis.RIGHTSTICKVERTICAL.getRawAxis(armController);
    double rotateValue = EnumeratedRawAxis.RIGHTSTICKHORIZONTAL.getRawAxis(armController);

    if (Math.abs(armWristValue) > 0.01 || Math.abs(rotateValue) > 0.01) {
      double actualArmWristVal = rotateValue;
      double actualRotateVal = -armWristValue;
      extendableArmAndWrist.move(actualArmWristVal, actualRotateVal);
    }

    double shoulderRotateValue = EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(armController);
    if (Math.abs(shoulderRotateValue) > 0.01) {
      extendableArmAndWrist.shoulderRotate(shoulderRotateValue);
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

}