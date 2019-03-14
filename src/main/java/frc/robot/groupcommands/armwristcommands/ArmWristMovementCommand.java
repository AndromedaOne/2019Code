package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class ArmWristMovementCommand extends CommandGroup {

  protected ArmWristMovementCommand(ArmPosition setpoint, boolean mirror) {

    this(setpoint, Robot.defaultShoulderPresetRange, Robot.defaultRetractionPresetRange, Robot.defaultWristPresetRange,
        mirror);

  }

  protected ArmWristMovementCommand(ArmPosition setpoint, double shoulderAngleRange, double retractionRange,
      double wristAngleRange, boolean mirror) {
    this(
        new ArmPosition(setpoint.getShoulderAngle() - shoulderAngleRange, setpoint.getArmRetraction() - retractionRange,
            setpoint.getWristAngle() - wristAngleRange),
        new ArmPosition(setpoint.getShoulderAngle() + shoulderAngleRange, setpoint.getArmRetraction() + retractionRange,
            setpoint.getWristAngle() + wristAngleRange),
        setpoint, mirror);
  }

  protected ArmWristMovementCommand(ArmPosition lowLimit, ArmPosition highLimit, ArmPosition setpoint, boolean mirror) {
    ArmPosition currentPosition = Robot.getCurrentArmPosition();

    if (currentPosition.isBetween(lowLimit, highLimit)) {
      goTo(setpoint);
    } else if (currentPosition.isBetween(highLimit.getMirror(), lowLimit.getMirror()) && mirror) {
      // the order of arguments is flipped above because everything is
      // negated when getMirror is called
      goTo(setpoint.getMirror());
    }
  }

  protected void goTo(ArmPosition position) {
    addSequential(new RotateShoulder(position.getShoulderAngle()));
    addSequential(new RotateWrist(position.getWristAngle()));
    addSequential(new RetractArm(position.getArmRetraction()));
  }
}