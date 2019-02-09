package frc.robot.closedloopcontrollers;

import frc.robot.sensors.NavXGyroSensor;
import frc.robot.subsystems.drivetrain.DriveTrain;

public class MoveDrivetrainGyroCorrect {

    private NavXGyroSensor navX;
    private DriveTrain driveTrain;
    private double savedAngle = 0;
    private double newForwardBackwardStickValue = 0;
    private double newRotateStickValue = 0;

    public MoveDrivetrainGyroCorrect(NavXGyroSensor theNavX, DriveTrain theDriveTrain) {
        navX = theNavX;
        driveTrain = theDriveTrain;
    }

    public void moveUsingGyro(double forwardBackward, double rotation,
     boolean useDelay, boolean useSquareInputs, double heading) {

        double robotDeltaAngle = navX.getCompassHeading() - heading;
        double robotAngle = navX.getZAngle() + robotDeltaAngle;
        double correctionEquation = savedAngle - robotAngle;
        if (!useDelay) {
			newForwardBackwardStickValue = forwardBackward;
			newRotateStickValue = correctionEquation;


		} else if (forwardBackward == 0 && rotation == 0) {

			savedAngle = robotAngle;
			newForwardBackwardStickValue = 0;
			newRotateStickValue = 0;
		} else if (rotation != 0) {
			courseCorrectionDelay = 0;

			savedAngle = robotAngle;
			newForwardBackwardStickValue = forwardBackward;
			newRotateStickValue = rotation;
		} else if (courseCorrectionDelay > 25) {
			// disable correction for half a second after releasing the turn stick, to allow
			// the driver
			// to let the machine drift naturally, and not correct back to the gyro reading
			// from
			// the instant the driver released the turn stick.

			// reassign the correctionEquation to the latest direction that we've been "free
			correctionEquation = (savedAngle - robotAngle)*kProportion;
			newForwardBackwardStickValue = forwardBackward;
			newRotateStickValue = correctionEquation;

		} else {
			// should all cases fail, just drive normally
			newForwardBackwardStickValue = forwardBackwardStickValue * mod;
			newRotateStickValue = rotateStickValue;
		}

    }
}