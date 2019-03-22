package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.armwristcommands.RetractArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class UnstowArm extends CommandGroup {

  public UnstowArm() {
    double armRetractAmount = 0;
    double shoulderRotateAmount = Robot.robotHasBall() ? CargoShipCargo.setpoint.getShoulderAngle()
        : LowHatch.setpoint.getShoulderAngle();
    double wristRotateAmount = Robot.robotHasBall() ? CargoShipCargo.setpoint.getWristAngle()
        : LowHatch.setpoint.getWristAngle();
    double finalRetractAmount = Robot.robotHasBall() ? CargoShipCargo.setpoint.getArmRetraction()
        : LowHatch.setpoint.getArmRetraction();
    // do stuff
    addSequential(new RetractArm(armRetractAmount));
    addParallel(new RotateShoulder(shoulderRotateAmount));
    addSequential(new RotateWrist(wristRotateAmount));
    addSequential(new RetractArm(finalRetractAmount));
  }
}