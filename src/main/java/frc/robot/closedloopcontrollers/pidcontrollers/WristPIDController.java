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
  private final MagEncoderSensor wristEncoder1;
  private final MagEncoderSensor wristEncoder2;

  private static WristPIDSource wristPIDSource;

  private WristPIDController() {
    super.absoluteTolerance = 3;
    super.p = 0;
    super.i = 0;
    super.d = 0;
    super.subsytemName = "Extendable Arm and Wrist";
    super.pidName = "Arm Extension";

    wristPIDSource = new WristPIDSource();

    wristEncoder1 = Robot.armExtensionEncoder1;
    wristEncoder2 = Robot.armExtensionEncoder2;
    super.trace = Trace.getInstance();
    wristEncoder1.putSensorOnLiveWindow(super.subsytemName, "Arm");
    wristEncoder2.putSensorOnLiveWindow(super.subsytemName, "Arm");
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
      trace.addTrace(true, "Shoulder PID", new TracePair("Output", output), new TracePair("Setpoint", _setpoint),
          new TracePair("Angle", wristEncoder1.pidGet()));
      MoveArmAndWristSafely.moveArmWristShoulder(0, 0, output);
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
      double difference = (wristEncoder1.pidGet() - wristEncoder2.pidGet());
      return difference;
    }
  }
}