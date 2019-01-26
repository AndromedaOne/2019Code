/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class PololuLineSensor {
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
        public synchronized void run() {
            while (true) {
                if (enabled) {
                    byte[] buffer = new byte[2 * NUM_SENSORS];
                    i2c.readOnly(buffer, 2 * NUM_SENSORS);
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
                Timer.delay(0.03);
            }
        }
    }
}
