package frc.robot.sensors.linefollowersensor;

public class MockLineFollowerSensorArray extends LineFollowerSensorBase {

  public MockLineFollowerSensorArray() {
    // These are bogus values
    super(1,1,1,2,20);
  }

  @Override
  public void getSensorReading(int[] readingBuf) {
    // This should be called once every 20ms
    System.out.println("Reading Mock Line Sensor");
  }

}