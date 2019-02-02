package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;

public class PololuSensorArray extends LineFollowerSensorArray {

/**
   * @param i2cBus A prebuilt I2C bus
   * @param detectionThreshold The minimum threshold for detecting a line
   * @param distanceToSensor The distance to the middle of the sensor from the
   * center of turning in centimetres
   * @param distanceBtSensors The distance between each individual sensor
   * @param numSensors The number of sensors in the array
   */
  public PololuSensorArray(I2C i2cBus, int detectionThreshold, double distanceToSensor, double distanceBtSensors,
      int numSensors) {
    super(i2cBus, detectionThreshold, distanceToSensor, distanceBtSensors, numSensors);
  }

  /**
   * This returns null. Don't use
   */
  @Override
  public boolean[] isThereLine() {
    boolean[] boolBuf = new boolean[0];

    return boolBuf;
  }
}