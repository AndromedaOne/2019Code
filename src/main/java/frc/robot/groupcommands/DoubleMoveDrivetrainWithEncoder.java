package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DoubleMoveDrivetrainWithEncoder extends CommandGroup {
    public DoubleMoveDrivetrainWithEncoder(double distanceInInches) {
        addSequential(new MoveUsingEncoderQuickly(distanceInInches));
        addSequential(new MoveUsingEncoderPrecisely(distanceInInches));
    }
}