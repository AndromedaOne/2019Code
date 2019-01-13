package frc.robot.Sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class UltrasonicSensor extends SensorBase<DoubleSensorSrc> {
    private Ultrasonic ultraSonic;

    private class UltrasonicSensorSrc extends DoubleSensorSrc {
        public Double getReading () {
            return getDistanceInches();
        }
    }
    private UltrasonicSensorSrc ultrasonicSensorSrc = new UltrasonicSensorSrc();

    public UltrasonicSensor (int ping, int echo){
        ultraSonic = new Ultrasonic(ping, echo);
    }
    public double getDistanceInches () {
        return ultraSonic.getRangeInches();
    }
    public DoubleSensorSrc getClosedLoopSrc () {
        return ultrasonicSensorSrc;
    }
}