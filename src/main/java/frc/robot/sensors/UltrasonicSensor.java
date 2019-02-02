package frc.robot.sensors;

import edu.wpi.first.wpilibj.Ultrasonic;

public class UltrasonicSensor implements PIDLoopable {
  private Ultrasonic ultraSonic;

  public UltrasonicSensor(int ping, int echo) {
    ultraSonic = new Ultrasonic(ping, echo);
  }

  public double getDistanceInches() {
    return ultraSonic.getRangeInches();
  }

  public double getClosedLoopSrc() {
    return getDistanceInches();

  }

  @Override
  public void reset() {
    return;
  }
}