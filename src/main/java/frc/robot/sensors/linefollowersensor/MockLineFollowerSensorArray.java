package frc.robot.sensors.linefollowersensor;

import edu.wpi.first.wpilibj.I2C;

public class MockLineFollowerSensorArray extends LineFollowerSensorBase {

  private int numSensors;

  public MockLineFollowerSensorArray(I2C i2cBus, int detectionThreshold, double distanceToSensor,
      double distanceBtSensors, int numSensors) {
    this.numSensors = numSensors;
  }

  @Override
  public boolean[] isThereLine() {
    boolean[] boolBuf = new boolean[numSensors - 1];
    for (int i = 0; i < boolBuf.length; i++) {
      boolBuf[i] = false;
    }
    return boolBuf;
  }

  @Override
  public LineFollowArraySensorReading getSensorReading() {
    LineFollowArraySensorReading lfsreading = new LineFollowArraySensorReading();
    lfsreading.lineAngle = 0;
    lfsreading.lineFound = false;
    return lfsreading;
  }

}