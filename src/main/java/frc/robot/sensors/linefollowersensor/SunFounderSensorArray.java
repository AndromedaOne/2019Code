package frc.robot.sensors.linefollowersensor;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SunFounderSensorArray extends LineFollowerSensorBase {

  private LineFollowerSensorBase lineSensor;
  private I2C mI2cBus;
  private byte[] buffer;
  private static final int NUM_SENSORS = 8;
  // Distance between sensors in centimeters
  private static final double DIST_BT_SENORS = 0;
  // Distance to sensors from center of rotation
  private static final double DIST_TO_SENSORS = 0;
  private static final int DETECTION_THRESHOLD = 0;

  public SunFounderSensorArray() {
    super(DETECTION_THRESHOLD, DIST_TO_SENSORS, DIST_BT_SENORS, NUM_SENSORS);
  }

  /**
   * @return An array of booleans where true means that a line was detected and
   *         false means that a line wasn't detected
   * @author Owen Salter
   */
  private boolean once = true;

  public boolean[] isThereLine() {
    boolean[] boolBuf = new boolean[buffer.length / 2];
    double[] dValues = new double[buffer.length / 2];
    // TODO: This is TOTALLY wrong, someone needs to fix it.
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
   *         angle in radians
   */
  public void getSensorReading(int[] readingBuf) {
    /*
     * Need to: - figure out adj from DistanceToSensor - get hyp from adj and op -
     * use hyp to calculate angle - return angle
     * 
     * boolean[] boolBuf = new boolean[(this.numSensors / 2) + 1]; int senseCount =
     * 0; double adj1 = 0;
     * 
     * boolBuf = isThereLine(); // Get the adjacent for the angles for (int i = 0; i
     * < boolBuf.length; i++) { if (boolBuf[i] == true) { adj1 = getAdjacent(i);
     * senseCount++; } }
     *
     * 
     * if (senseCount != 0) { sensorReading.lineFound = true; } else {
     * sensorReading.lineFound = false; }
     */

  }
}