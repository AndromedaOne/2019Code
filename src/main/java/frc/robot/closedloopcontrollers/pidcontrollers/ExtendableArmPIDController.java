package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.sensors.SensorBase;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;

public class ExtendableArmPIDController extends PIDControllerBase {

  private static ExtendableArmPIDController instance;
  private static ArmPIDOut armPIDOut;
  private static ArmPIDSource armPIDSource;
  private final MagEncoderSensor topArmEncoder;
  private final MagEncoderSensor bottomArmEncoder;

  private ExtendableArmPIDController() {
    super.absoluteTolerance = 0.5 / Robot.EXTENSIONINCHESPERTICK;
    // PID loop will only return true if error is within 1.5 inches of setpoint
    super.p = 1.0 * Math.pow(10, -4); // 5.0e-5;
    super.i = 0;
    super.d = 0.0;// 1.0e-4;
    super.outputRange = 0.85;
    super.subsystemName = "Extendable Arm and Wrist";
    super.pidName = "Extension";

    topArmEncoder = Robot.topArmExtensionEncoder;
    bottomArmEncoder = Robot.bottomArmExtensionEncoder;
    armPIDSource = new ArmPIDSource();
    armPIDSource.putSensorOnLiveWindow(super.subsystemName, "Extension");
    // topArmEncoder.putSensorOnLiveWindow(super.subsytemName,
    // "ExtensionTopEncoder");
    // bottomArmEncoder.putSensorOnLiveWindow(super.subsytemName,
    // "ExtensionBottomEncoder");
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
      /*
       * Trace.getInstance().addTrace(true, "ExtensionPID", new TracePair("Output",
       * output), new TracePair("SetpointTicks", pidMultiton.getSetpoint()), new
       * TracePair("SetpointInches", pidMultiton.getSetpoint() *
       * Robot.EXTENSIONINCHESPERTICK), new TracePair("ExtensionTicks",
       * armPIDSource.pidGet()), new TracePair("ExtensionInches",
       * armPIDSource.pidGet() * Robot.EXTENSIONINCHESPERTICK));
       */
      // try {
      MoveArmAndWristSafely.setPidExtensionPower(output);
      // } catch (ArmOutOfBoundsException e) {
      // System.out.println(e.getMessage());
      // container.disable();
      // }
    }
  }

  public static ExtendableArmPIDController getInstance() {
    System.out.println(" --- Asking for Extendable Arm PID Instance ---");
    if (instance == null) {
      System.out.println("Creating new ExtendableArm PID Controller");
      instance = new ExtendableArmPIDController();
      instance.setName(instance.subsystemName, instance.pidName);
      LiveWindow.add(instance);
    }
    return instance;
  }

  private class ArmPIDSource extends SensorBase implements PIDSource {

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
      double extensionTicks = currentArmPosition.getArmRetraction() / Robot.EXTENSIONINCHESPERTICK;
      return extensionTicks;
    }

    @Override
    public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
      putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "PidGet", this::pidGet);
    }

  }

  @Override
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / Robot.EXTENSIONINCHESPERTICK);
  }

}
