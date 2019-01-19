package frc.robot;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import frc.robot.Robot;

public class RobotTest {
  @Test
  public void configTest() {
    String robotName = Robot.getName();
    assertEquals("Paul", robotName);
  }

  @Test
  public void unsignedByteTest(){

    byte[] a1 = {0,0,(byte)0x7F,(byte)0x0A,0,0,0,0,0,0};
    assertEquals(32522, twoUnsignedBytesToInt(a1[2], a1[3]));
    assertEquals(32522L, byteArrayToLong(a1));

    byte[] a2 = { 0,0,(byte)0x8B,(byte)0x9C, 0,0,0,0,0,0};
    assertEquals(35740, twoUnsignedBytesToInt(a2[2], a2[3]));
    assertEquals(35740L, byteArrayToLong(a2));
  }

  public int twoUnsignedBytesToInt(byte hob, byte lob){
    return (Byte.toUnsignedInt(hob) << 8) + Byte.toUnsignedInt(lob);
  }

  public int byteArrayToLong(byte[] a){
    return ByteBuffer.wrap(a).getInt();
  }
}