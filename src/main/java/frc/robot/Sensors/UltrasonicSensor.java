package frc.robot.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class UltrasonicSensor extends SensorBase<DoubleSensorSrc> {
    private Ultrasonic ultraSonic;

    public UltrasonicSensor (int ping, int echo){
        ultraSonic = new Ultrasonic(ping, echo);
    }
    public double getDistanceInches () {
        return ultraSonic.getRangeInches();
    }
    public DoubleSensorSrc getClosedLoopSrc () {
        return new DoubleSensorSrc(){
        
            @Override
            public Double getReading() {
                return getDistanceInches();
            }
        };
    }
}