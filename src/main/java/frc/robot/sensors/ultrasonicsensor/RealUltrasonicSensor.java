package frc.robot.sensors.ultrasonicsensor;

import edu.wpi.first.wpilibj.Ultrasonic;

public class RealUltrasonicSensor extends UltrasonicSensor {
  private Ultrasonic ultrasonic;

  protected String subsystemName;
  protected String sensorName;

  /**
   * Creates the ultrasonic with the ping and echo ports passed in
   * 
   * @param ping Port for the ping
   * @param echo Port for the echo
   */
  public RealUltrasonicSensor(int ping, int echo) {
    ultrasonic = new Ultrasonic(ping, echo);
    ultrasonic.setEnabled(true);
    ultrasonic.setAutomaticMode(true);
  }

  @Override
  public double getDistanceInches() {
    double distance = ultrasonic.getRangeInches();
    return distance;
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    System.out.println("Real Ultrasonic");
    super.putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "Inches:", this::getDistanceInches);
  }
}