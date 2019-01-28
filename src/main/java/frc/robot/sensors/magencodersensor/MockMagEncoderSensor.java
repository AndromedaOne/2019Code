package frc.robot.sensors.magencodersensor;

public class MockMagEncoderSensor extends MagEncoderSensor {

  @Override
  public double getDistanceInches() {
    return 0;
  }

  @Override
  public double getDistanceTicks() {
    return 0;
  }

  @Override
  public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    System.out.println("Cannot put the sensor named: " + sensorNameParam +
    "on live window because it does not exist");

  }

}