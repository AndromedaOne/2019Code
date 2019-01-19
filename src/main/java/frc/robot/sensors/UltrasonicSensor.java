package frc.robot.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class UltrasonicSensor implements PIDLoopable {
    private Ultrasonic ultrasonic;

    public UltrasonicSensor(int ping, int echo){
        ultrasonic = new Ultrasonic(ping, echo);
    }
    public double getDistanceInches() {
        return ultrasonic.getRangeInches();
    }
    public double getClosedLoopSrc() {
        return getDistanceInches();

    }

    @Override
    public void reset() {
        return;
    }
}