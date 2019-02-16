/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors.linefollowersensor;

import java.time.Duration;
import java.time.Instant;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class PololuLineSensor extends LineFollowerSensorBase {
  private static I2C i2c = new I2C(I2C.Port.kOnboard, 2);
  private static final int NUM_SENSORS = 8;
  private static Thread readThread;
  private static boolean enabled = false;

  public void setEnabled() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }

  public PololuLineSensor() {
    readThread = new ReadDataThread();
    readThread.start();
  }

  private static class ReadDataThread extends Thread {

    private long timeAccumulated = 0;
    private final int timesAveraged = 50;

    public synchronized void run() {
      int i = 0;
      while (true) {
        if (enabled) {
          byte[] buffer = new byte[2 * NUM_SENSORS];
          Instant startTime = Instant.now();
          i2c.readOnly(buffer, 2 * NUM_SENSORS);

          // This calculates the time it takes the to read the sensor
          // This is average for each second
          // This is mainly test code
          Instant endTime = Instant.now();
          Duration timeElapsed = Duration.between(startTime, endTime);
          timeAccumulated += timeElapsed.toMillis();
          if (i < timesAveraged) {
            System.out.println(timeElapsed.toMillis());
            ++i;
          } else {
            System.out.println("Average Time : " + timeAccumulated / (timesAveraged + 1));
            timeAccumulated = 0;
            i = 0;
          }

          // This calulates the deconstructed bytes that come from the arduino
          for (int sensorNumber = 0; sensorNumber < NUM_SENSORS; ++sensorNumber) {
            int b = 0;
            for (int count = 0; count < 2; ++count) {
              int a = buffer[sensorNumber * 2 + count] & 0xFF;
              b = b | a;
              if (count != 1) {
                b = b << 8;
              }
            }
            System.out.print(b + "\t");
          }
          System.out.println();
        }
        Timer.delay(0.02);
      }
    }
  }

  @Override
  public boolean[] isThereLine() {
    return null;
  }

  @Override
  public LineFollowArraySensorReading getSensorReading() {
    return null;
  }
}
