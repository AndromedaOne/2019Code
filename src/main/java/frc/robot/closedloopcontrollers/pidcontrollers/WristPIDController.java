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

public class WristPIDController {

  private static WristPIDController instance;
  private WristPIDOut wristPIDOut;
  private final MagEncoderSensor topArmEncoder;
  private final MagEncoderSensor bottomArmEncoder;
  private PIDMultiton pidMultiton;

  private static WristPIDSource wristPIDSource;

  private WristPIDController() {
    double absoluteTolerance = 3.0 / Robot.WRISTDEGREESPERTICK;
    // PID loop will only return true if error is within 5 degrees of setpoint
    double p = 1.05e-4;
    double i = 0.0; // 2.0e-6;
    double d = 0.0; // 1.0e-5;
    double outputRange = 0.6;
    String subsystemName = "Extendable Arm and Wrist";
    String pidName = "Wrist";

    PIDConfiguration pidConfiguration = new PIDConfiguration(p, i, d, 0, 0, 1, absoluteTolerance, subsystemName,
        pidName);

    pidMultiton = PIDMultiton.getInstance(wristPIDSource, wristPIDOut, pidConfiguration);

    wristPIDSource = new WristPIDSource();
    wristPIDSource.putSensorOnLiveWindow(subsystemName, "WristAngle");

    topArmEncoder = Robot.topArmExtensionEncoder;
    bottomArmEncoder = Robot.bottomArmExtensionEncoder;
    wristPIDOut = new WristPIDOut();
    wristPIDOut.setContainer(pidMultiton);
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

  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / Robot.WRISTDEGREESPERTICK);
  }

  public void enable() {
    pidMultiton.enable();
  }

  public boolean onTarget() {
    return pidMultiton.onTarget();
  }
}
