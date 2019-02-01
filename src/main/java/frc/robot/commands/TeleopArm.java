package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.extendablearmandwrist.ExtendableArmAndWrist;
import frc.robot.utilities.EnumeratedRawAxis;

public class TeleopArm extends Command{

    Joystick armController = Robot.armController;
    ExtendableArmAndWrist extendableArmAndWrist = Robot.extendableArmAndWrist;

    public TeleopArm() {
        requires(Robot.extendableArmAndWrist);
    }

    @Override
    protected void execute() {
        double armWristValue = EnumeratedRawAxis.RIGHTSTICKHORIZONTAL.getRawAxis(armController);
        double rotateValue = EnumeratedRawAxis.LEFTSTICKHORIZONTAL.getRawAxis(armController);

        extendableArmAndWrist.move(armWristValue, rotateValue);
    }
    @Override
    protected boolean isFinished() {
        return false;
    }

}