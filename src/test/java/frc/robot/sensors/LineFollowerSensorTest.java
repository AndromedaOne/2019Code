package frc.robot.sensors;

/*
Potential Test Cases
- Different Geometries
- What happens if it's right in front of us
- "     "       "  we're perpendicular to it
*/

import static org.junit.Assert.*;

import org.junit.Test;

import frc.robot.sensors.LineFollowerSensorArray.*;
import frc.robot.utilities.*;

public class LineFollowerSensorTest {
    FakeI2CBus i2cBus = new FakeI2CBus(9);
    LineFollowerSensorArray lfs = new LineFollowerSensorArray(i2cBus, 20, 10, 0.5, 8);

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

    // Next two tests: Value = atan(1.75/10) = x
    @Test
    public void getSensorReadingLeftTest() {
        i2cBus.setBuffer(new byte[]{100,0,0,0,0,0,0,0});
        LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
        assertTrue(sensorReading.lineFound);
        assertEquals(0.173246, sensorReading.lineAngle, 0.001);
    }

    @Test
    public void getSensorReadingRightTest() {
        i2cBus.setBuffer(new byte[]{0,0,0,0,0,0,0,100});
        LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
        assertTrue(sensorReading.lineFound);
        assertEquals(-0.173246, sensorReading.lineAngle, 0.001);
    }

    // Different geometry
    @Test
    public void getSensorReadingDiffGeoTest() {
        LineFollowerSensorArray lfs = new LineFollowerSensorArray(i2cBus, 20, 15, 3, 8);
        i2cBus.setBuffer(new byte[]{15,14,16,25,26,17,16,15});
        LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
        assertEquals(Math.atan2(-1.5, 15), sensorReading.lineAngle, 0.001);
    }

    // Real data, fake measurements
    @Test
    public void getSensorReadingRealNumsTest() {
        LineFollowerSensorArray lfs = new LineFollowerSensorArray(i2cBus, 50, 10, 0.5, 8);
        i2cBus.setBuffer(new byte[]{22,26,32,31,33,41,58,64});
        LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
        assertEquals(Math.atan2(1.75, 10), sensorReading.lineAngle, 0.001);
    }

    // Try it with different number of sensors
    @Test
    public void getSensorReadingMoreSensorsTest() {
        LineFollowerSensorArray lfs = new LineFollowerSensorArray(i2cBus, 50, 10, 0.5, 10);
        i2cBus.setBuffer(new byte[]{22,26,32,31,33,41,58,64,70});
        LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
        assertEquals(Math.atan2(1.25, 10), sensorReading.lineAngle, 0.001);
    }
}