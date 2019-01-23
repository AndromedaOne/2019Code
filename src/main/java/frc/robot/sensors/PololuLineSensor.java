/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;

/**
 * Add your docs here.
 */
public class PololuLineSensor {
    private I2C i2c = new I2C(I2C.Port.kOnboard, 2);
    private final int NUM_SENSORS = 3;

    public PololuLineSensor() {

    }

    public void readData() {
        byte[] buffer = new byte[4];
        for (int sensorNumber = 0; sensorNumber < NUM_SENSORS; ++sensorNumber) {
            i2c.readOnly(buffer, 4);
            int b = 0;
            for(int count = 0; count <= 3; ++count) {
                int a = buffer[count] & 0xFF;
                b = b | a;
                if (count != 3) {
                    b = b << 8;
                }
            }
            System.out.println("Sensor " + (sensorNumber + 1)  + " = " + b);
        }
    }
}