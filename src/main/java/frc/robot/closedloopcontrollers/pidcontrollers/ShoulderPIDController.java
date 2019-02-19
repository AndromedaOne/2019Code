package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.exceptions.ArmOutOfBoundsException;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class ShoulderPIDController extends PIDControllerBase {

  private static ShoulderPIDController instance;
  private ShoulderPIDOut shoulderPIDOut;
  private ShoulderPIDSource shoulderPIDSrc;
  private MagEncoderSensor shoulderEncoder;

  private ShoulderPIDController() {
    super.absoluteTolerance = 3;
    super.p = 1.0 * Math.pow(10, -4);
    super.i = 0;
    super.d = 0;
    super.subsytemName = "Extendable Arm and Wrist";
    super.pidName = "ShoulderPID";

    super.trace = Trace.getInstance();
    shoulderPIDOut = new ShoulderPIDOut();
    shoulderEncoder = Robot.shoulderEncoder;
    shoulderPIDSrc = new ShoulderPIDSource();
    super.setPIDConfiguration(super.pidConfiguration);
    shoulderEncoder.putSensorOnLiveWindow(super.subsytemName, "ShoulderEncoder");
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
      trace.addTrace(true, "ShoulderPID", new TracePair("Output", output * 10000),
          new TracePair("SetpointTicks", container.getSetpoint()),
          new TracePair("SetpointDegrees", container.getSetpoint() * MoveArmAndWristSafely.SHOULDERDEGREESPERTICK),
          new TracePair("AngleTicks", shoulderPIDSrc.pidGet()),
          new TracePair("AngleDegrees", shoulderPIDSrc.pidGet() * MoveArmAndWristSafely.SHOULDERDEGREESPERTICK));
      //try {
        MoveArmAndWristSafely.setPidShoulderPower(output);
      //} catch (ArmOutOfBoundsException e) {
        //System.out.println(e.getMessage());
        //container.disable();
      //}
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
      double shoulderDegrees = MoveArmAndWristSafely.getShoulderRotDeg(shoulderEncoder.pidGet());
      double shoulderTicks = shoulderDegrees / MoveArmAndWristSafely.SHOULDERDEGREESPERTICK;
      return shoulderTicks;
    }
  }

  @Override
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / MoveArmAndWristSafely.SHOULDERDEGREESPERTICK);
  }

}