package frc.robot.closedloopcontrollers;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.LineFollowerSensorArray;

public class LineFollowerController implements ClosedLoopControllerBase {
    private DriveTrain driveTrain;
    private LineFollowerSensorArray sensor;
    private final double kMinimumLineAngle = Math.toRadians(10);
    private final double kForwardSpeed = .5;
    private final double kRotateAmount = .5; //all constants are currently placeholders

    public LineFollowerController (DriveTrain driveTrain1, LineFollowerSensorArray lineFollowerSensorArray) {
    driveTrain = driveTrain1;
    sensor = lineFollowerSensorArray;

    }
    
    public void run() {
        LineFollowerSensorArray.LineFollowArraySensorReading v = sensor.getSensorReading();
        SmartDashboard.putBoolean("IsLineFound", v.lineFound);
        SmartDashboard.putNumber("Angle", v.lineAngle);
        if(v.lineFound = true) {
            //System.out.println("I FOUND A LINE!! :D");
            if(v.lineAngle <= -kMinimumLineAngle) {
                driveTrain.move(kForwardSpeed, kRotateAmount);
            }
            else if(v.lineAngle >= kMinimumLineAngle) {
                driveTrain.move(kForwardSpeed, -kRotateAmount);
            }
            else {
                driveTrain.move(kForwardSpeed, 0);
            }
        } 
        else {
            System.out.println("Line Not Found! :c");
            driveTrain.move(0, 0);
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