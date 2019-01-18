package frc.robot.closedloopcontrollers;
import frc.robot.subsystems.DriveTrain;
import frc.robot.sensors.LineFollowerSensorArray;

public class LineFollowerController implements ClosedLoopControllerBase {
    private DriveTrain mDriveTrain;
    private LineFollowerSensorArray mSensor;

    public LineFollowerController (DriveTrain driveTrain, LineFollowerSensorArray lineFollowerSensorArray) {
    mDriveTrain = driveTrain;
    mSensor = lineFollowerSensorArray;

    }
    
    public void run() {
        LineFollowerSensorArray.LineFollowArraySensorReading v = getSensorReading();
        if(v.lineFound = true) {
            if(v.lineAngle <= -10) {
                mDriveTrain.move(5.0, 10.0); //5.0 and 10.0 are placeholder numbers
            }
        }


    }

    public void reset() {

    }

    public void stop() {

    }

    public void initialize() {

    }

    public boolean isDone() {
        return false;
    }


    
}