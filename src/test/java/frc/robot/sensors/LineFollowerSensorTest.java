package frc.robot.sensors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import frc.robot.sensors.LineFollowerSensorArray;
import frc.robot.sensors.LineFollowerSensorArray.LineFollowArraySensorReading;
import frc.robot.utilities.*;

public class LineFollowerSensorTest {
    FakeI2CBus i2cBus = new FakeI2CBus(9);
    LineFollowerSensorArray lfs = new LineFollowerSensorArray(i2cBus, 20);

    @Test
    public void isThereLineTest() {
        boolean boolBufTest[] = new boolean[8];
        // Set data
        boolBufTest[0] = false;
        boolBufTest[1] = false;
        boolBufTest[2] = false;
        boolBufTest[3] = true;
        boolBufTest[4] = true;
        boolBufTest[5] = false;
        boolBufTest[6] = false;
        boolBufTest[7] = false;

        /*       
        buffer[0] = 15;
        buffer[2] = 14;
        buffer[4] = 16;
        buffer[6] = 25;
        buffer[8] = 26;
        buffer[10] = 17;
        buffer[12] = 16;
        buffer[14] = 15;
        */
        i2cBus.setBuffer(new byte[]{15,14,16,25,26,17,16,15,});
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
        i2cBus.setBuffer(new byte[]{15,14,16,25,26,17,16,15});
        assertTrue(lfs.getSensorReading().lineFound);    
    }

    @Test
    public void getSensorReadingLeftTest() {
        i2cBus.setBuffer(new byte[]{100,0,0,0,0,0,0,0});
        LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
        assertTrue(sensorReading.lineFound);
        assertEquals(5.0, sensorReading.lineAngle, 5.0);
    }
}