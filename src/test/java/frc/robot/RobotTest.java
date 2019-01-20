package frc.robot;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import frc.robot.Robot;

public class RobotTest {
  @Test
  public void configTest() {
    String robotName = Robot.getName();
    assertEquals("DEFAULTCONFIG", robotName);
  }
}