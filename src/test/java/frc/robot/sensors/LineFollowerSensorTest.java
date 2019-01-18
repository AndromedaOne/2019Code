package frc.robot.sensors;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import frc.robot.sensors.LineFollowerSensor;
import frc.robot.utilities.I2CBusDriver;

public class LineFollowerSensorTest {
    I2CBusDriver i2cBusDriver = new I2CBusDriver(true, 9);
    LineFollowerSensor lfs = new LineFollowerSensor(i2cBusDriver);

    @Test
    public void isThereLineTest() {
        boolean boolBufTest[] = new boolean[8];
        // Set data
        for (int i = 0; i < boolBufTest.length; i++) {
            boolBufTest[i] = false;
        }

        boolean[] boolBuf = lfs.isThereLine();
        assertArrayEquals(boolBufTest, boolBuf);
    }
}