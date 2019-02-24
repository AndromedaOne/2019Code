package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class WristPIDController extends PIDControllerBase {

  private static WristPIDController instance;
  private WristPIDOut wristPIDOut;
  private final MagEncoderSensor topArmEncoder;
  private final MagEncoderSensor bottomArmEncoder;

  private static WristPIDSource wristPIDSource;

  private WristPIDController() {
    super.absoluteTolerance = 5 / MoveArmAndWristSafely.WRISTDEGREESPERTICK;
    // PID loop will only return true if error is within 5 degrees of setpoint
    super.p = 1.0e-4;
    super.i = 0;
    super.d = 0;
    super.outputRange = 0.75;
    super.subsytemName = "Extendable Arm and Wrist";
    super.pidName = "Wrist";

    wristPIDSource = new WristPIDSource();

    topArmEncoder = Robot.topArmExtensionEncoder;
    bottomArmEncoder = Robot.bottomArmExtensionEncoder;
    super.trace = Trace.getInstance();
    topArmEncoder.putSensorOnLiveWindow(super.subsytemName, "WristTopEncoder");
    bottomArmEncoder.putSensorOnLiveWindow(super.subsytemName, "WristBottomEncoder");
    wristPIDOut = new WristPIDOut();
    super.setPIDConfiguration(super.pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(wristPIDSource, wristPIDOut, super.pidConfiguration);
    wristPIDOut.setContainer(super.pidMultiton);
  }

  private class WristPIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "WristPID", new TracePair("Output", output),
          new TracePair("SetpointTicks", container.getSetpoint()),
          new TracePair("SetpointDegrees", container.getSetpoint() * MoveArmAndWristSafely.WRISTDEGREESPERTICK),
          new TracePair("TicksAngle", wristPIDSource.pidGet()), new TracePair("DegreeAngle",
              MoveArmAndWristSafely.getWristRotDegrees(topArmEncoder.pidGet(), bottomArmEncoder.pidGet())));
      // try {
      MoveArmAndWristSafely.setPidWristPower(output);
      // } catch (ArmOutOfBoundsException e) {
      // System.out.println(e.getMessage());
      // container.disable();
      // }
    }
  }

  public static WristPIDController getInstance() {
    System.out.println(" --- Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Wrist PID Controller");
      instance = new WristPIDController();
    }
    return instance;
  }

  private class WristPIDSource implements PIDSource {

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
      double wristDegrees = MoveArmAndWristSafely.getWristRotDegrees(topArmEncoder.pidGet(), bottomArmEncoder.pidGet());
      double wristTicks = wristDegrees / MoveArmAndWristSafely.WRISTDEGREESPERTICK;
      return wristTicks;
    }
  }

  @Override
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / MoveArmAndWristSafely.WRISTDEGREESPERTICK);
  }
}