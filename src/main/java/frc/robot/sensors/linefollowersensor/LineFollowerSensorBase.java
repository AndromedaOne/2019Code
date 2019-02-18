package frc.robot.sensors.linefollowersensor;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class LineFollowerSensorBase {

  public abstract void getSensorReading(int[] readingBuf);

  private LineFollowArraySensorReading sensorReading = new LineFollowArraySensorReading();
  private GetSensorData sensorDataThread;
  private double currentDistanceFromCenter = 0;
  // Default Distance to sensor array in centimetres
  protected double distanceToSensor;
  // Distance between sensors in centimetres
  protected double distanceBtSensors;
  protected int detectionThreshold;
  protected int numSensors;
  protected double threadTime = 0;
  protected int[] sensorReadingBuffer;

  protected LineFollowerSensorBase(int detectionThreshold, double distanceToSensor, double distanceBtSensors, int numSensors, double threadTime) {
    this.detectionThreshold = detectionThreshold;
    this.distanceToSensor = distanceToSensor;
    this.distanceBtSensors = distanceBtSensors;
    this.numSensors = numSensors;
    this.threadTime = threadTime;
    sensorDataThread = new GetSensorData();
  }

  private class GetSensorData extends Thread {
    int[] mySensorReadingBuffer = new int[numSensors];
    int[] returnSensorReadingBuffer = new int[numSensors];
    public void run() {
      while(true) {
        getSensorReading(mySensorReadingBuffer);
        updateSensorReadingBuffer();
        Timer.delay(threadTime);
      }
    }
    private synchronized void updateSensorReadingBuffer() {
      returnSensorReadingBuffer = mySensorReadingBuffer.clone();
    }

    public synchronized int[] getSensorReadingBuffer() {
      return returnSensorReadingBuffer;
    } 
  }

  /**
   * Determines if an integer is even or odd
   */
  private static Boolean isEven (Integer i) {
    return (i % 2) == 0;
  }


  /**
   * Gets the distance from the center of the sensor (assuming 0 is the leftmost
   * sensor and there are an even number of sensors)
   */
  private double calculateDistanceFromCenter(int i) {
    double distFromSensor;
    // Get the number of sensors on each side of the center
    if(isEven(numSensors)) {
      int halfNumSensors = (numSensors + 1) / 2;
      // If it's on the left...
      if (i < halfNumSensors) {
        distFromSensor = (halfNumSensors - i);
        distFromSensor = ((distFromSensor * distanceBtSensors) - distanceBtSensors / 2);
        return distFromSensor;
        // If it's on the right...
      } else {
        distFromSensor = (halfNumSensors - i) - 1;
        distFromSensor = ((distFromSensor * distanceBtSensors) - distanceBtSensors / 2);
        return distFromSensor;
      }
    } else {
      // There is no case for an odd number of sensors yet
      System.err.println("ERROR: Line Sensors with an odd number of sensors are not supported");
      return 0;
    }
  }

  private double calculateLineAngleFromCenter(double distFromCenter) {
    double angle = Math.atan2(distFromCenter, distanceToSensor);
    return angle;
  }

  private boolean isThereLine() {
    boolean[] lineArray = getLinePositionArray();
    for(int i = 0; i < sensorReadingBuffer.length; i++) {
      if(lineArray[i]) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return An array of booleans for each sensor in the sensor array where true
   *  means that a line was detected and false means that a line wasn't detected
   * @author Owen Salter & Devin C
   */
  private boolean[] getLinePositionArray() {
    boolean[] boolBuf = new boolean[sensorReadingBuffer.length];

    for (int i = 0; i < sensorReadingBuffer.length; i++) {
      if (sensorReadingBuffer[i] >= detectionThreshold) {
        currentDistanceFromCenter = calculateDistanceFromCenter(i);
        boolBuf[i] = true;
      } else {
        boolBuf[i] = false;
      }
    }
    return boolBuf;
  }

  /**
   * @return This will return wether or not we found a line and what angle we found the line at
   * as a type LineFollowArraySensorReading
   */
  public LineFollowArraySensorReading findLine() {
    sensorReadingBuffer = sensorDataThread.getSensorReadingBuffer();
    sensorReading.lineFound = isThereLine();
    sensorReading.lineAngle = calculateLineAngleFromCenter(currentDistanceFromCenter);
    return sensorReading;
  }

}