package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class IntakeReady extends CommandGroup {

  ArmPosition setpoint = new ArmPosition(-10.67, 20.56, -90);
  ArmPosition lowerLimit = new ArmPosition(-20, 15, -100);
  ArmPosition upperLimit = new ArmPosition(0, 25, -80);

  public IntakeReady() {
    ArmPosition currentPosition = Robot.getCurrentArmPosition();

    if (currentPosition.isBetween(lowerLimit, upperLimit)) {
      addSequential(new RotateWrist(setpoint.getWristAngle()));
      addSequential(new RetractArm(setpoint.getArmRetraction()));
      addSequential(new RotateShoulder(setpoint.getShoulderAngle()));
    }

  }
}