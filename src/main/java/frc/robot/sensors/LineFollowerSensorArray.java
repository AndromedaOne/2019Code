package frc.robot.sensors;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LineFollowerSensorArray {
  private I2C mI2cBus;
  private byte[] buffer;
  // Default Distance to sensor array in centimetres
  private double distanceToSensor;
  // Distance between sensors in centimetres
  private double distanceBtSensors;
  private int detectionThreshold;
  private int numSensors;

  /**
   * @param i2cBus A prebuilt I2C bus
   * @param detectionThreshold The minimum level required for activation of the
   * sensor
   * @param distanceToSensor The distance from the centre of turning to the sensor
   * @param distanceBtSensors The distance between each sensor
   * @param numSensors the number of sensors in the array
   * @author Owen Salter
   */
  public LineFollowerSensorArray(I2C i2cBus, int detectionThreshold, double distanceToSensor, double distanceBtSensors,
      int numSensors) {
    mI2cBus = i2cBus;
    this.detectionThreshold = detectionThreshold;
    this.distanceToSensor = distanceToSensor;
    this.distanceBtSensors = distanceBtSensors;
    this.numSensors = numSensors;
    this.buffer = new byte[(numSensors * 2)];
  }

  /**
   * @return An array of booleans where true means that a line was detected and
   * false means that a line wasn't detected
   * @author Owen Salter
   */
    private boolean once = true;
  public boolean[] isThereLine() {
    boolean[] boolBuf = new boolean[buffer.length / 2];
    double[] dValues = new double[buffer.length / 2];

    mI2cBus.readOnly(buffer, (numSensors * 2) - 1);
        if (mI2cBus.readOnly(buffer, 16) == false) {
            if (once) {
                System.out.println("readOnly returned false");
                once = false;
            }
        }
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
   * Doesn't do anything right now.
   * 
   * @return structure containing a boolean of whether the line is found, and the
   * angle in radians
   */
  public LineFollowArraySensorReading getSensorReading() {
    /*
     * Need to: - figure out adj from DistanceToSensor - get hyp from adj and op -
     * use hyp to calculate angle - return angle
     */
    boolean[] boolBuf = new boolean[(this.numSensors / 2) + 1];
    int senseCount = 0;
    double adj1 = 0;

    boolBuf = isThereLine();
    // Get the adjacent for the angles
    for (int i = 0; i < boolBuf.length; i++) {
      if (boolBuf[i] == true) {
        adj1 = getAdjacent(i);
        senseCount++;
      }
    }

    double angle = Math.atan2(adj1, distanceToSensor);
    LineFollowArraySensorReading sensorReading = new LineFollowArraySensorReading();
    sensorReading.lineAngle = angle;

    if (senseCount != 0) {
      sensorReading.lineFound = true;
    } else {
      sensorReading.lineFound = false;
    }
    return sensorReading;
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
      return distFromSensor /** = -1 */
      ;
    }

  }

  public class LineFollowArraySensorReading {
    public boolean lineFound;
    public double lineAngle;
  }

}