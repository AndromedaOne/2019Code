package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class ExtendableArmPIDController extends PIDControllerBase {

  private static ExtendableArmPIDController instance;
  private static ArmPIDOut armPIDOut;
  private static ArmPIDSource armPIDSource;
  private final MagEncoderSensor armEncoder1;
  private final MagEncoderSensor armEncoder2;

  private ExtendableArmPIDController() {
    super.absoluteTolerance = 3;
    super.p = 0;
    super.i = 0;
    super.d = 0;
    super.subsytemName = "Extendable Arm and Wrist";
    super.pidName = "Arm Extension";

    armEncoder1 = Robot.armExtensionEncoder1;
    armEncoder2 = Robot.armExtensionEncoder2;
    super.trace = Trace.getInstance();
    armPIDSource = new ArmPIDSource();
    armEncoder1.putSensorOnLiveWindow(super.subsytemName, "Arm");
    armEncoder2.putSensorOnLiveWindow(super.subsytemName, "Arm");
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
      trace.addTrace(true, "Arm PID", new TracePair("Output", output), new TracePair("Setpoint", _setpoint),
          new TracePair("Extension 1", armPIDSource.pidGet()));
      MoveArmAndWristSafely.move(output, 0, 0);
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
      double average = (armEncoder1.pidGet() + armEncoder2.pidGet()) / 2;
      return average;
    }

  }

}
