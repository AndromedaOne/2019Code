package frc.robot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RobotTest {
  @Test
  public void configTest() {
    String robotName = Robot.getName();
    assertEquals("DEFAULTCONFIG", robotName);
  }
}