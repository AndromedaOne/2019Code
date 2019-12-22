package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.*;
import frc.robot.sensors.SensorBase;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;

public class DrivetrainEncoderPIDController {

  private static DrivetrainEncoderPIDController instance;
  private EncoderPIDOut encoderPIDOut;
  private EncoderPIDIn encoderPIDSrc;
  private MagEncoderSensor encoder;
  private final double kMinOut = 0.125;
  public static final double TICKSTOINCHESRATIO = 2064.1216;
  private PIDMultiton pidMultiton;

  /**
   * Sets the encoder, encoderPIDOut, trace, and pidConfiguration variables. Also
   * creates the encoderPID from the PIDMultiton class.
   */
  private DrivetrainEncoderPIDController() {

    PIDConfiguration pidConfiguration = new PIDConfiguration(0.004, 0, 0, 0, kMinOut, 1, 1, "EncoderPIDHeader",
        "EncoderPID");

    encoder = Robot.drivetrainLeftRearEncoder;
    encoderPIDOut = new EncoderPIDOut();
    encoderPIDSrc = new EncoderPIDIn();
    encoderPIDSrc.putSensorOnLiveWindow(pidConfiguration.getLiveWindowName(), "PIDin");
    pidMultiton = PIDMultiton.getInstance(encoderPIDSrc, encoderPIDOut, pidConfiguration);
  }

  private class EncoderPIDIn extends SensorBase implements PIDSource {

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
      return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {

      return encoder.getDistanceTicks() / TICKSTOINCHESRATIO;
    }

    @Override
    public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
      putReadingOnLiveWindow(subsystemNameParam, sensorNameParam, this::pidGet);

    }

  }

  private class EncoderPIDOut implements PIDOutput {
    /**
     * Sets the pidWrite to write all of the PID's output to the drivetrain move
     * method. Also it traces the output, setpoint, and Encoder Ticks
     */
    @Override
    public void pidWrite(double input) {
      double output = input;
      if (output > 0) {
        output = (output * (1 - kMinOut) + kMinOut);
      } else {
        output = (output * (1 - kMinOut) - kMinOut);
      }

      if (input == 0) {
        System.out.println("Input = 0");
        Robot.driveTrain.stop();
        output = 0;
      } else {
        Robot.gyroCorrectMove.moveUsingGyro(output, 0, false, false);
      }
    }
  }

  /**
   * Gets the instance of DrivetrainEncoderPIDController
   * 
   * @return instance
   */
  public static DrivetrainEncoderPIDController getInstance() {
    System.out.println(" --- Asking for Drivetrain EncoderP PID Instance --- ");
    if (instance == null) {
      System.out.println("Creating new DriveTrain Encoder PID Controller");
      instance = new DrivetrainEncoderPIDController();
    }
    return instance;
  }

  public PIDMultiton getPIDMultiton() {
    return pidMultiton;
  }

}