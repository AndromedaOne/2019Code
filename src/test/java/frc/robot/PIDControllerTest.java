package frc.robot;

import org.junit.Test;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import static org.junit.Assert.assertEquals;

import frc.robot.Robot;
import frc.robot.closedloopcontrollers.ClosedLoopControllerBase;
import frc.robot.closedloopcontrollers.PIDConfiguration;
import frc.robot.closedloopcontrollers.PIDMultiton;

public class PIDControllerTest {
  @Test
  public void pidControllerTest() throws InterruptedException {
    PIDConfiguration pidConfiguration = new PIDConfiguration();
    pidConfiguration.p = 1;
    pidConfiguration.i = 0;
    pidConfiguration.d = 0;
    pidConfiguration.minimumOutput = 0;
    pidConfiguration.maximumOutput = 2;
    pidConfiguration.absoluteTolerance = 3;
    pidConfiguration.LiveWindowName = "Thanos";

    PIDSource  source = new PIDSource() {

      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {

      }

      @Override
      public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
      }

      @Override
      public double pidGet() {
        return 0;
		}

    };

    MockPIDOutput output = new MockPIDOutput();
    

    PIDMultiton pid = PIDMultiton.getInstance(source, output, pidConfiguration);
    pid.enable(1.0);
    Thread.sleep(1000);
    assertEquals(1.0, output.outputTest, 0.0001);
  }
  public static class MockPIDOutput implements PIDOutput {

      public double outputTest = 0;

      @Override
      public void pidWrite(double output) {
        outputTest = output;
      }

    };  
}