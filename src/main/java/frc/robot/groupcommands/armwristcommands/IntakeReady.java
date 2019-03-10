package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class IntakeReady extends CommandGroup {

  final static ArmPosition setpoint = new ArmPosition(62.5, 21.9, -38.6);
  final static ArmPosition lowerLimit = new ArmPosition(45.0, 15.9, -55.0);
  final static ArmPosition upperLimit = new ArmPosition(80.0, 29.0, -21.6);

  public IntakeReady() {
    ArmPosition currentPosition = Robot.getCurrentArmPosition();

    if (currentPosition.isBetween(lowerLimit, upperLimit)) {
      addSequential(new RotateWrist(setpoint.getWristAngle()));
      addSequential(new RetractArm(setpoint.getArmRetraction()));
      addSequential(new RotateShoulder(setpoint.getShoulderAngle()));
    }

  }
}