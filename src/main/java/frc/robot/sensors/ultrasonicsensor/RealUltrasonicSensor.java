package frc.robot.sensors.ultrasonicsensor;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public abstract class RealUltrasonicSensor extends UltrasonicSensor {
  private Ultrasonic ultraSonic;

  protected String subsystemName;
  protected String sensorName;

  public RealUltrasonicSensor(int ping, int echo) {
    ultraSonic = new Ultrasonic(ping, echo);
  }

  public double getDistanceInches() {
    return ultraSonic.getRangeInches();
  }

  public void putOnSmartDashboard(String subsystemNameParam, String sensorNameParam) {
    subsystemName = subsystemNameParam;
    sensorName = sensorNameParam;
    LiveWindow.add(this);
    this.setName(sensorName);
  }
}