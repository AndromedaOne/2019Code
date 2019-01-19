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

    byte[] a1 = {(byte)0x0A, (byte)0x7F,0,0,0,0,0,0};
    assertEquals(32522, twoUnsignedBytesToInt(a1[1], a1[0]));
    //assertEquals(32522L, byteArrayToLong(a1));

    byte[] a2 = { (byte)0x9C,(byte)0x8B, 0,0,0,0,0,0};
    assertEquals(35740, twoUnsignedBytesToInt(a2[1], a2[0]));
    //assertEquals(32522L, byteArrayToLong(a2));
  }

  public int twoUnsignedBytesToInt(byte hob, byte lob){
    return (Byte.toUnsignedInt(hob) << 8) + Byte.toUnsignedInt(lob);
  }

  public int byteArrayToLong(byte[] a){
    return ByteBuffer.wrap(a).getInt();
  }
}