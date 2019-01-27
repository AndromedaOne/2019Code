package frc.robot.sensors.ultrasonicsensor;

public class MockUltrasonicSensor extends UltrasonicSensor {

  @Override
  public double getDistanceInches() {
    return 0;
  }

  @Override
  public void putOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putOnLiveWindow(subsystemNameParam, sensorNameParam);
    System.out
        .println("The Ultrasonic named: " + sensorNameParam + " does not exist so it cannot be added to Live Window");
  }

}