package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.*;
import frc.robot.sensors.SensorBase;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;

public class ExtendableArmPIDController {

  private static ExtendableArmPIDController instance;
  private static ArmPIDOut armPIDOut;
  private static ArmPIDSource armPIDSource;
  private final MagEncoderSensor topArmEncoder;
  private final MagEncoderSensor bottomArmEncoder;
  private PIDMultiton pidMultiton;

  private ExtendableArmPIDController() {
    double absoluteTolerance = 0.5 / Robot.EXTENSIONINCHESPERTICK;
    // PID loop will only return true if error is within 1.5 inches of setpoint
    double p = 1.0 * Math.pow(10, -4);
    double i = 0;
    double d = 0.0;// 1.0e-4;
    double outputRange = 0.85;
    String subsystemName = "Extendable Arm and Wrist";
    String pidName = "Extension";

    PIDConfiguration pidConfiguration = new PIDConfiguration(p, i, d, 0, 0, 1, 1, subsystemName, pidName);

    topArmEncoder = Robot.topArmExtensionEncoder;
    bottomArmEncoder = Robot.bottomArmExtensionEncoder;
    armPIDSource = new ArmPIDSource();
    armPIDSource.putSensorOnLiveWindow(subsystemName, "Extension");
    armPIDOut = new ArmPIDOut();
    armPIDOut.setContainer(pidMultiton);

    pidMultiton = PIDMultiton.getInstance(armPIDSource, armPIDOut, pidConfiguration);
  }

  private class ArmPIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
      MoveArmAndWristSafely.setPidExtensionPower(output);
    }
  }

  public static ExtendableArmPIDController getInstance() {
    System.out.println(" --- Asking for Extendable Arm PID Instance ---");
    if (instance == null) {
      System.out.println("Creating new ExtendableArm PID Controller");
      instance = new ExtendableArmPIDController();
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

  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / Robot.EXTENSIONINCHESPERTICK);
  }

  public void enable() {
    pidMultiton.enable();
  }

  public boolean onTarget() {
    return pidMultiton.onTarget();
  }

}
