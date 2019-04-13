package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.*;

public class DoubleMoveDrivetrainWithEncoder extends CommandGroup {
    public DoubleMoveDrivetrainWithEncoder(double distanceInInches) {
        addSequential(new MoveUsingEncoderQuickly(distanceInInches));
        addSequential(new MoveUsingEncoderPrecisely(distanceInInches));
    }

    public void initialize() {
        Robot.driveTrain.setGear(DriveTrain.RobotGear.LOWGEAR);
    }
}