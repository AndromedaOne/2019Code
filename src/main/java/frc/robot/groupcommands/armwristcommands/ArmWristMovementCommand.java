package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class ArmWristMovementCommand extends CommandGroup {

  protected ArmWristMovementCommand(ArmPosition lowLimit, ArmPosition highLimit, ArmPosition setpoint) {
    ArmPosition currentPosition = Robot.getCurrentArmPosition();

    if (currentPosition.isBetween(lowLimit, highLimit)) {
      goTo(setpoint);
    } else if (currentPosition.isBetween(highLimit.getMirror(), lowLimit.getMirror())) {
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