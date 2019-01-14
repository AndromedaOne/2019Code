import static org.junit.Assert.assertEquals;
import org.junit.Test;

import frc.robot.Robot;

public class RobotTest {
  @Test
  public void configTest() {
    String robotName = Robot.getName();
    assertEquals("Paul", robotName);
  }
}