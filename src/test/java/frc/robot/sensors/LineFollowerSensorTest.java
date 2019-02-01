package frc.robot.sensors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import frc.robot.utilities.*;

public class LineFollowerSensorTest {
  FakeI2CBus i2cBus = new FakeI2CBus(9);
  LineFollowerSensorArray lfs = new LineFollowerSensorArray(i2cBus, 20);

  @Ignore("Not getting valid stuff")
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
    boolBufTest[8] = false;

    boolean[] boolBuf = lfs.isThereLine();
    assertArrayEquals(boolBufTest, boolBuf);
  }

  @Ignore
  @Test
  public void getSensorReadingTest() {
    /*
     * Needs to: - pass dummy i2c device - have dummy i2c device return predefined
     * data - Make sure math works
     */
    System.out.println(lfs.getSensorReading().lineFound);
    assertTrue(lfs.getSensorReading().lineFound);
  }
}