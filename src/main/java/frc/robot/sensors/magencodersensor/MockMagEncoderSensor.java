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
  public void putOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
    super.putOnLiveWindow(subsystemNameParam, sensorNameParam);
    System.out
        .println("The encoder named: " + sensorNameParam + " does not exist so it cannot be added to smart Dashboard");
  }

}