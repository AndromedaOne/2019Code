package frc.robot.closedloopcontrollers;
import frc.robot.subsystems.DriveTrain;
import frc.robot.sensors.LineFollowerSensorArray;

public class LineFollowerController implements ClosedLoopControllerBase {
    private DriveTrain driveTrain;
    private LineFollowerSensorArray sensor;
    private final double kMinimumLineAngle = Math.toRadians(10);
    private final double kForwardSpeed = .1;
    private final double kRotateAmount = .1; //all constants are currently placeholders

    public LineFollowerController (DriveTrain driveTrain1, LineFollowerSensorArray lineFollowerSensorArray) {
    driveTrain = driveTrain1;
    sensor = lineFollowerSensorArray;

    }
    
    public void run() {
        LineFollowerSensorArray.LineFollowArraySensorReading v = sensor.getSensorReading();
        if(v.lineFound = true) {
            if(v.lineAngle <= -kMinimumLineAngle) {
                driveTrain.move(kForwardSpeed, kRotateAmount);
            }
            else if(v.lineAngle >= kMinimumLineAngle) {
                driveTrain.move(kForwardSpeed, -kRotateAmount);
            }
            else {
                driveTrain.move(kForwardSpeed, 0);
            }
        } else {
            driveTrain.move(0, 0);
            System.out.println("Line Not Found!");
        }


    }

    public void reset() {

    }

    public void stop() {
        driveTrain.stop();

    }

    public void initialize() {

    }

    public boolean isDone() {
        return false;
    }


    
}