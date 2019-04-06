package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.sensors.SensorBase;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;

public class WristPIDController extends PIDControllerBase {

  private static WristPIDController instance;
  private WristPIDOut wristPIDOut;
  private final MagEncoderSensor topArmEncoder;
  private final MagEncoderSensor bottomArmEncoder;

  private static WristPIDSource wristPIDSource;

  private WristPIDController() {
    super.absoluteTolerance = 3.0 / Robot.WRISTDEGREESPERTICK;
    // PID loop will only return true if error is within 5 degrees of setpoint
    super.p = 1.05e-4; // 5.0e-5;
    super.i = 0.0; // 2.0e-6;
    super.d = 0.0; // 1.0e-5;
    super.outputRange = 0.6;
    super.subsystemName = "Extendable Arm and Wrist";
    super.pidName = "Wrist";

    wristPIDSource = new WristPIDSource();
    wristPIDSource.putSensorOnLiveWindow(super.subsystemName, "WristAngle");

    topArmEncoder = Robot.topArmExtensionEncoder;
    bottomArmEncoder = Robot.bottomArmExtensionEncoder;
    // topArmEncoder.putSensorOnLiveWindow(super.subsytemName, "WristTopEncoder");
    // bottomArmEncoder.putSensorOnLiveWindow(super.subsytemName,
    // "WristBottomEncoder");
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
      ArmPosition currentArmPosition = Robot.getCurrentArmPosition();
      MoveArmAndWristSafely.setPidWristPower(output);
    }
  }

  public static WristPIDController getInstance() {
    System.out.println(" --- Asking for WristPID Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Wrist PID Controller");
      instance = new WristPIDController();
    }
    return instance;
  }

  private class WristPIDSource extends SensorBase implements PIDSource {

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
      double wristTicks = currentArmPosition.getWristAngle() / Robot.WRISTDEGREESPERTICK;
      return wristTicks;
    }

    @Override
    public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
      putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "PidGet", this::pidGet);
    }
  }

  @Override
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / Robot.WRISTDEGREESPERTICK);
  }
}
