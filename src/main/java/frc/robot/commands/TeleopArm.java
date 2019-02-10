package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
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

    double actualArmWristVal = rotateValue;
    double actualRotateVal = -armWristValue;
    double shoulderRotateValue = EnumeratedRawAxis.LEFTSTICKVERTICAL.getRawAxis(armController);
    if (Math.abs(shoulderRotateValue) < 0.01) {
      shoulderRotateValue = 0.0;
    }
    MoveArmAndWristSafely.move(actualArmWristVal, actualRotateVal, shoulderRotateValue);
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
    MoveArmAndWristSafely.move(0, 0, 0);
  }

}