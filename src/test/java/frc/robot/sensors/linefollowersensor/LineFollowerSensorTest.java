package frc.robot.sensors.linefollowersensor;

/*
Potential Test Cases
- Different Geometries
- What happens if it's right in front of us
- "     "       "  we're perpendicular to it
*/

import static org.junit.Assert.*;

import org.junit.Test;

import frc.robot.sensors.linefollowersensor.SunFounderSensorArray.*;
import frc.robot.utilities.*;

public class LineFollowerSensorTest {
  FakeI2CBus i2cBus = new FakeI2CBus(9);
  SunFounderSensorArray lfs = new SunFounderSensorArray(i2cBus, 20, 10, 0.5, 8);

  @Test
  public void isThereLineTest() {
    // Set data for testing
    boolean boolBufTest[] = new boolean[] { false, false, false, true, true, false, false, false };
    // Set data to be passed in
    i2cBus.setBuffer(new byte[] { 15, 14, 16, 25, 26, 17, 16, 15, });
    boolean[] boolBuf = lfs.isThereLine();
    assertArrayEquals(boolBufTest, boolBuf);
  }

  @Test
  public void belowThresholdTest() {
    boolean[] boolBufTest = new boolean[] { false, false, false, false, false, false, false, false };

    i2cBus.setBuffer(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 });

    boolean[] boolBuf = lfs.isThereLine();

    assertArrayEquals(boolBufTest, boolBuf);
  }

  @Test
  public void getSensorReadingTest() {
    /*
     * Needs to: - pass dummy i2c device - have dummy i2c device return predefined
     * data - Make sure math works
     */
    i2cBus.setBuffer(new byte[] { 15, 14, 16, 25, 26, 17, 16, 15 });
    assertTrue(lfs.lineFound());
  }

  // Next two tests: Value = atan(1.75/10) = x
  @Test
  public void getSensorReadingLeftTest() {
    i2cBus.setBuffer(new byte[] { 100, 0, 0, 0, 0, 0, 0, 0 });
    assertTrue(lfs.lineFound());
    assertEquals(0.173246, lfs.lineAngle(), 0.001);
  }

  @Test
  public void getSensorReadingRightTest() {
    i2cBus.setBuffer(new byte[] { 0, 0, 0, 0, 0, 0, 0, 100 });
    LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
    assertTrue(sensorReading.lineFound);
    assertEquals(-0.173246, sensorReading.lineAngle, 0.001);
  }

  // Different geometry
  @Test
  public void getSensorReadingDiffGeoTest() {
    SunFounderSensorArray lfs = new SunFounderSensorArray(i2cBus, 20, 15, 3, 8);
    i2cBus.setBuffer(new byte[] { 15, 14, 16, 25, 26, 17, 16, 15 });
    LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
    assertEquals(Math.atan2(-1.5, 15), sensorReading.lineAngle, 0.001);
  }

  // Real data, fake measurements
  @Test
  public void getSensorReadingRealNumsTest() {
    SunFounderSensorArray lfs = new SunFounderSensorArray(i2cBus, 50, 10, 0.5, 8);
    i2cBus.setBuffer(new byte[] { 22, 26, 32, 31, 33, 41, 58, 64 });
    LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
    assertEquals(Math.atan2(-1.75, 10), sensorReading.lineAngle, 0.001);
  }

  // Try it with different number of sensors
  @Test
  public void getSensorReadingMoreSensorsTest() {
    SunFounderSensorArray lfs = new SunFounderSensorArray(i2cBus, 50, 10, 0.5, 10);
    i2cBus.setBuffer(new byte[] { 22, 24, 26, 32, 31, 33, 41, 58, 64, 70 });
    LineFollowArraySensorReading sensorReading = lfs.getSensorReading();
    assertEquals(Math.atan2(-2.25, 10), sensorReading.lineAngle, 0.001);
  }
}