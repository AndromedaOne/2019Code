package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;

public class DrivetrainEncoderPIDController extends PIDControllerBase {

  private static DrivetrainEncoderPIDController instance;
  private EncoderPIDOut encoderPIDOut;
  private EncoderPIDIn encoderPIDSrc;
  private MagEncoderSensor encoder;

  private static final double TICKSTOINCHESRATIO = 0;

  /**
   * Sets the encoder, encoderPIDOut, trace, and pidConfiguration variables. Also
   * creates the encoderPID from the PIDMultiton class.
   */
  private DrivetrainEncoderPIDController() {
    super.absoluteToleranceForQuickMovement = 100;
    super.pForMovingQuickly = 0.0001;
    super.iForMovingQuickly = 0.00001;
    super.dForMovingQuickly = 0;

    super.absoluteToleranceForPreciseMovement = 100;
    super.pForMovingPrecisely = 0.0001;
    super.iForMovingPrecisely = 0.00001;
    super.dForMovingPrecisely = 0;


    super.subsystemName = "EncoderPIDHeader";
    super.pidName = "EncoderPID";

    encoder = Robot.drivetrainLeftRearEncoder;
    encoder.putSensorOnLiveWindow(super.subsystemName, "LeftRearEncoder");
    encoderPIDOut = new EncoderPIDOut();
    encoderPIDSrc = new EncoderPIDIn();
    pidConfiguration = new PIDConfiguration();
    super.setPIDConfiguration(super.pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(encoderPIDSrc, encoderPIDOut, super.pidConfiguration);
  }

  private class EncoderPIDIn implements PIDSource {

    

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
      return null;
    }

    @Override
    public double pidGet() {
      return encoder.getDistanceTicks() * TICKSTOINCHESRATIO;
	}

  }

  private class EncoderPIDOut implements PIDOutput {
    /**
     * Sets the pidWrite to write all of the PID's output to the drivetrain move
     * method. Also it traces the output, setpoint, and Encoder Ticks
     */
    @Override
    public void pidWrite(double output) {
      Robot.gyroCorrectMove.moveUsingGyro(output, 0, false, false);
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

}