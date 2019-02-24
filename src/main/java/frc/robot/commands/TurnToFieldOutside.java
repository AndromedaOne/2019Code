package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.groupcommands.autopaths.AutoStartingConfig;

public class TurnToFieldOutside extends TurningBase {
    private static GyroPIDController gyroPID = GyroPIDController.getInstance();

    public TurnToFieldOutside() {
        requires(Robot.driveTrain);
    }

    protected void initialize() {
        if (AutoStartingConfig.onRightSide) {
            turnToCompassHeading(90);
        } else {
            turnToCompassHeading(270);
        }
    }

    @Override
    protected boolean isFinished() {
        return gyroPID.onTarget();
    }

}