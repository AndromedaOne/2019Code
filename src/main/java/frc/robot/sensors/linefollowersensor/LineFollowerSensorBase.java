package frc.robot.sensors.linefollowersensor;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class LineFollowerSensorBase {

  public abstract void getSensorReading(int[] readingBuf);

  private LineFollowArraySensorReading sensorReading = new LineFollowArraySensorReading();
  private GetSensorData sensorDataThread;
  // Default Distance to sensor array in centimetres
  protected double distanceToSensor;
  // Distance between sensors in centimetres
  protected double distanceBtSensors;
  protected int detectionThreshold;
  protected int numSensors;
  protected int threadTime = 0;
  public int[] sensorReadingBuffer;

  protected LineFollowerSensorBase(int detectionThreshold, double distanceToSensor, double distanceBtSensors, int numSensors, int threadTime) {
    this.detectionThreshold = detectionThreshold;
    this.distanceToSensor = distanceToSensor;
    this.distanceBtSensors = distanceBtSensors;
    this.numSensors = numSensors;
    this.threadTime = threadTime;
    sensorDataThread = new GetSensorData();
  }

  private class GetSensorData extends Thread {
    public synchronized void run() {
      getSensorReading(sensorReadingBuffer);
      Timer.delay(threadTime);
    }
  }

  /**
   * @return An array of booleans where true means that a line was detected and
   *         false means that a line wasn't detected
   * @author Owen Salter, Devin Cannava, & Ethan McFetridge
   */
  public boolean[] isThereLine() {
    boolean[] boolBuf = new boolean[sensorReadingBuffer.length / 2];
    double[] dValues = new double[sensorReadingBuffer.length / 2];
    // Step through each even-numbered element in the array
    for (int i = 0; i < sensorReadingBuffer.length / 2; i++) {
      if (sensorReadingBuffer[i * 2] >= 0) {
        dValues[i] = sensorReadingBuffer[i * 2];
      } else {
        dValues[i] = sensorReadingBuffer[i * 2] + 256;
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
   * Gets the distance from the center of the sensor (assuming 0 is the leftmost
   * sensor and there are an even number of sensors)
   */
  private double getDistanceFromCenter(int i) {
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
    double distFromSensor = getDistanceFromCenter(i);
    double tempAdj = 0;
    if (distFromSensor >= 0) {
      tempAdj = (distFromSensor) - (distanceBtSensors / 2);
    } else {
      tempAdj = (distFromSensor) + (distanceBtSensors / 2);
    }
    return tempAdj;
  }
  // Angle math
  // double angle = Math.atan2(adj1, distanceToSensor);
  // sensorReading.lineAngle = angle;

}