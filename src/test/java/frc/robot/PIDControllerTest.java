package frc.robot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.closedloopcontrollers.PIDConfiguration;
import frc.robot.closedloopcontrollers.PIDMultiton;

public class PIDControllerTest {
  @Test
  public void pidControllerTest() throws InterruptedException {
    PIDConfiguration pidConfiguration = new PIDConfiguration();
    pidConfiguration.setP(1);
    pidConfiguration.setI(0);
    pidConfiguration.setD(0);
    pidConfiguration.setMinimumOutput(0);
    pidConfiguration.setMaximumOutput(2);
    pidConfiguration.setAbsoluteTolerance(3);
    pidConfiguration.setLiveWindowName("Thanos");

    PIDSource source = new PIDSource() {

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
    pid.setSetpoint(1.0);
    pid.enable();
    Thread.sleep(2000);
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