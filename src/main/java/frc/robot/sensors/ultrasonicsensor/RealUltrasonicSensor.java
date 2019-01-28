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

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Inches:", 
    this::getDistanceInches);
  }
}