package frc.robot.Sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class GyroSensor extends SensorBase<DoubleSensorSrc> {
    private  NavxGyro navX;

    private class GyroSensorSrc extends DoubleSensorSrc {
        public Double getReading () {
            return getDistanceInches();
        }
    }
    private GyroSensorSrc gyroSensorSrc = new GyroSensorSrc();

    public GyroSensor (int ping, int echo){
       
    }
    public double getZangle () {
        return ultraSonic.getRangeInches();
    }
    public DoubleSensorSrc getClosedLoopSrc () {
        return gyroSensorSrc;
    }
}