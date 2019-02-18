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

public class ExtendableArmPIDController extends PIDControllerBase {

  private static ExtendableArmPIDController instance;
  private static ArmPIDOut armPIDOut;
  private static ArmPIDSource armPIDSource;
  private final MagEncoderSensor topArmEncoder;
  private final MagEncoderSensor bottomArmEncoder;

  private ExtendableArmPIDController() {
    super.absoluteTolerance = 3;
    super.p = 0;//2.5 * Math.pow(10, -4);
    super.i = 0;
    super.d = 0;
    super.subsytemName = "Extendable Arm and Wrist";
    super.pidName = "Extension";

    topArmEncoder = Robot.topArmExtensionEncoder;
    bottomArmEncoder = Robot.bottomArmExtensionEncoder;
    super.trace = Trace.getInstance();
    armPIDSource = new ArmPIDSource();
    topArmEncoder.putSensorOnLiveWindow(super.subsytemName, "ExtensionTopEncoder");
    bottomArmEncoder.putSensorOnLiveWindow(super.subsytemName, "ExtensionBottomEncoder");
    armPIDOut = new ArmPIDOut();
    super.setPIDConfiguration(super.pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(armPIDSource, armPIDOut, super.pidConfiguration);
    armPIDOut.setContainer(super.pidMultiton);
  }

  private class ArmPIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "ExtensionPID", new TracePair("Output", output),
          new TracePair("SetpointTicks", pidMultiton.getSetpoint()),
          new TracePair("SetpointInches", pidMultiton.getSetpoint() * MoveArmAndWristSafely.EXTENSIONTICKSTOINCHES),
          new TracePair("ExtensionTicks", armPIDSource.pidGet()),
          new TracePair("ExtensionInches", armPIDSource.pidGet() * MoveArmAndWristSafely.EXTENSIONTICKSTOINCHES));
      try {
        MoveArmAndWristSafely.move(output, 0, 0, MoveArmAndWristSafely.DontUsePIDHold.EXTENSION);
      } catch (ArmOutOfBoundsException e) {
        System.out.println(e.getMessage());
        container.disable();
      }
    }
  }

  public static ExtendableArmPIDController getInstance() {
    System.out.println(" --- Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Intake PID Controller");
      instance = new ExtendableArmPIDController();
    }
    return instance;
  }

  private class ArmPIDSource implements PIDSource {

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
      double extensionInches = MoveArmAndWristSafely.getExtensionIn(topArmEncoder.pidGet(), bottomArmEncoder.pidGet());
      double extensionTicks = extensionInches / MoveArmAndWristSafely.EXTENSIONTICKSTOINCHES;
      return extensionTicks;
    }

  }

  @Override
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / MoveArmAndWristSafely.EXTENSIONTICKSTOINCHES);
  }

}
