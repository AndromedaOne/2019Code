package frc.robot.sensors.linefollowersensor;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class LineFollowerSensorBase {

  public abstract void getSensorReading(int[] readingBuf);

  protected I2C mI2cBus;
  protected byte[] buffer;
  // Default Distance to sensor array in centimetres
  protected double distanceToSensor;
  // Distance between sensors in centimetres
  protected double distanceBtSensors;
  protected int detectionThreshold;
  protected int numSensors;

  /**
   * @return An array of booleans where true means that a line was detected and
   *         false means that a line wasn't detected
   * @author Owen Salter, Devin Cannava, & Ethan McFetridge
   */
  public boolean[] isThereLine() {
    boolean[] boolBuf = new boolean[buffer.length / 2];
    double[] dValues = new double[buffer.length / 2];
    // Step through each even-numbered element in the array
    for (int i = 0; i < buffer.length / 2; i++) {
      if (buffer[i * 2] >= 0) {
        dValues[i] = buffer[i * 2];
      } else {
        dValues[i] = buffer[i * 2] + 256;
      }

    }

    SmartDashboard.putNumberArray("LineFollowArray", dValues);
    // Check for whether the line is found
    for (int i = 0; i < dValues.length; i++) {
      if (dValues[i] >= detectionThreshold) {
        boolBuf[i] = true;
      } else {
        boolBuf[i] = false;
      }
    }
    return boolBuf;
  }

  /**
   * Gets the distance from the centre of the sensor (assuming 0 is the leftmost
   * sensor and there are an even number of sensors)
   */
  private double getDistanceFromCentre(int i) {
    double distFromSensor;
    // Get the number of sensors on each side of the center
    int halfNumSensors = (numSensors + 1) / 2;
    // If it's on the left...
    if (i < halfNumSensors) {
      distFromSensor = (halfNumSensors - i);
      distFromSensor = (distFromSensor * distanceBtSensors);
      return distFromSensor;
      // If it's on the right...
    } else {
      distFromSensor = (halfNumSensors - i) - 1;
      distFromSensor = (distFromSensor * distanceBtSensors);
      return distFromSensor;
    }
  }

  private double getAdjacent(int i) {
    double distFromSensor = getDistanceFromCentre(i);
    double tempAdj = 0;
    if (distFromSensor >= 0) {
      tempAdj = (distFromSensor) - (distanceBtSensors / 2);
    } else {
      tempAdj = (distFromSensor) + (distanceBtSensors / 2);
    }
    return tempAdj;
  }

}