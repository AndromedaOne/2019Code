package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;

public class ShoulderPIDController extends PIDControllerBase {

  private static ShoulderPIDController instance;
  private ShoulderPIDOut shoulderPIDOut;
  private ShoulderPIDSource shoulderPIDSrc;
  private MagEncoderSensor shoulderEncoder;

  private ShoulderPIDController() {
    super.absoluteTolerance = 0.5 / Robot.SHOULDERDEGREESPERTICK;
    // PID loop will only return true if error is within 5 degrees of setpoint
    super.p = 1.0 * Math.pow(10, -4);
    super.i = 0;
    super.d = 0;
    super.subsystemName = "Extendable Arm and Wrist";
    super.pidName = "ShoulderPID";

    shoulderPIDOut = new ShoulderPIDOut();
    shoulderEncoder = Robot.shoulderEncoder;
    shoulderPIDSrc = new ShoulderPIDSource();
    super.setPIDConfiguration(super.pidConfiguration);
    shoulderEncoder.putSensorOnLiveWindow(super.subsystemName, "ShoulderEncoder");
    super.pidMultiton = PIDMultiton.getInstance(shoulderPIDSrc, shoulderPIDOut, super.pidConfiguration);
    shoulderPIDOut.setContainer(super.pidMultiton);
  }

  private class ShoulderPIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
          new TracePair("SetpointDegrees", container.getSetpoint() * Robot.SHOULDERDEGREESPERTICK),
          new TracePair("AngleDegrees", shoulderPIDSrc.pidGet() * Robot.SHOULDERDEGREESPERTICK));
      // try {
      MoveArmAndWristSafely.setPidShoulderPower(output);
      // } catch (ArmOutOfBoundsException e) {
      // System.out.println(e.getMessage());
      // container.disable();
      // }
    }
  }

  public static ShoulderPIDController getInstance() {
    System.out.println(" --- Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Shoulder PID Controller");
      instance = new ShoulderPIDController();
    }
    return instance;
  }

  private class ShoulderPIDSource implements PIDSource {

    @Override
    /**
     * Does not do anything
     */
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    /**
     * @return kDisplacement because that is what we use for all of our PID
     * Controllers
     */
    public PIDSourceType getPIDSourceType() {
      return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
      ArmPosition currentArmPosition = Robot.getCurrentArmPosition();
      double shoulderTicks = currentArmPosition.getShoulderAngle() / Robot.SHOULDERDEGREESPERTICK;
      return shoulderTicks;
    }
  }

  @Override
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / Robot.SHOULDERDEGREESPERTICK);
  }

}