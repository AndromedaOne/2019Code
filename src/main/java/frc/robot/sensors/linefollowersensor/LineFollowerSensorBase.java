package frc.robot.sensors.linefollowersensor;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class LineFollowerSensorBase {
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
   * false means that a line wasn't detected
   * @author Owen Salter
   */
  private boolean once = true;  

  public boolean[] isThereLine(){
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

  public abstract LineFollowArraySensorReading getSensorReading();

  
}