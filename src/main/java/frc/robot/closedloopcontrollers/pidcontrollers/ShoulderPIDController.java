package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.ArmPosition;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveArmAndWristSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.*;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;

public class ShoulderPIDController {

  private static ShoulderPIDController instance;
  private ShoulderPIDOut shoulderPIDOut;
  private ShoulderPIDSource shoulderPIDSrc;
  private MagEncoderSensor shoulderEncoder;
  private PIDMultiton pidMultiton;

  private ShoulderPIDController() {
    double absoluteTolerance = 3.5 / Robot.SHOULDERDEGREESPERTICK;
    // PID loop will only return true if error is within 5 degrees of setpoint
    double p = 1.0 * Math.pow(10, -4);
    double i = 0;
    double d = 0;
    String subsystemName = "Extendable Arm and Wrist";
    String pidName = "ShoulderPID";

    PIDConfiguration pidConfiguration = new PIDConfiguration(p, i, d, 0, 0, 1, absoluteTolerance, subsystemName,
        pidName);

    shoulderPIDOut = new ShoulderPIDOut();
    shoulderEncoder = Robot.shoulderEncoder;
    shoulderPIDSrc = new ShoulderPIDSource();
    shoulderEncoder.putSensorOnLiveWindow(subsystemName, "ShoulderEncoder");
    shoulderPIDOut.setContainer(pidMultiton);

    pidMultiton = PIDMultiton.getInstance(shoulderPIDSrc, shoulderPIDOut, pidConfiguration);
  }

  private class ShoulderPIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
      MoveArmAndWristSafely.setPidShoulderPower(output);
    }
  }

  public static ShoulderPIDController getInstance() {
    System.out.println(" --- Asking for Shoulder PID Instance --- ");
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

  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint / Robot.SHOULDERDEGREESPERTICK);
  }

  public void enable() {
    pidMultiton.enable();
  }

  public boolean onTarget() {
    return pidMultiton.onTarget();
  }

}
