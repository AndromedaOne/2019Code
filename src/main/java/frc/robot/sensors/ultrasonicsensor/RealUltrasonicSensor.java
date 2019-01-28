package frc.robot.sensors.ultrasonicsensor;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public abstract class RealUltrasonicSensor extends UltrasonicSensor {
  private Ultrasonic ultrasonic;

  protected String subsystemName;
  protected String sensorName;

  /**
   * Creates the ultrasonic with the ping and echo ports passed in
   * @param ping
   * @param echo
   */
  public RealUltrasonicSensor(int ping, int echo) {
    ultrasonic = new Ultrasonic(ping, echo);
  }

  @Override
  public double getDistanceInches() {
    return ultrasonic.getRangeInches();
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Inches:", 
    this::getDistanceInches);
  }
}