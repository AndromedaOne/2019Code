package frc.robot.sensors;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import frc.robot.sensors.LineFollowerSensorArray;
import frc.robot.utilities.*;

public class LineFollowerSensorTest {
    FakeI2CBusDriver i2cBusDriver = new FakeI2CBusDriver(true, 9);
    LineFollowerSensorArray lfs = new LineFollowerSensorArray(i2cBusDriver);

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

    @Test
    public void getSensorReadingTest() {
        /*
        Needs to:
        - pass dummy i2c device
        - have dummy i2c device return predefined data
        - Make sure math works
        */
        
    }
}