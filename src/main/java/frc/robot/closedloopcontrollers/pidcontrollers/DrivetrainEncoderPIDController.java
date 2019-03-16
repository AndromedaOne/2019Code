package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.telemetries.TracePair;

public class DrivetrainEncoderPIDController extends PIDControllerBase {

  private static DrivetrainEncoderPIDController instance;
  private EncoderPIDOut encoderPIDOut;
  private MagEncoderSensor encoder;

  /**
   * Sets the encoder, encoderPIDOut, trace, and pidConfiguration variables. Also
   * creates the encoderPID from the PIDMultiton class.
   */
  private DrivetrainEncoderPIDController() {
    super.absoluteTolerance = 100;
    super.p = 0.0001;
    super.i = 0.00001;
    super.d = 0;
    super.subsystemName = "EncoderPIDHeader";
    super.pidName = "EncoderPID";

    encoder = Robot.drivetrainLeftRearEncoder;
    encoder.putSensorOnLiveWindow(super.subsystemName, "LeftRearEncoder");
    encoderPIDOut = new EncoderPIDOut();
    pidConfiguration = new PIDConfiguration();
    super.setPIDConfiguration(super.pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(encoder, encoderPIDOut, super.pidConfiguration);
  }

  private class EncoderPIDOut implements PIDOutput {
    /**
     * Sets the pidWrite to write all of the PID's output to the drivetrain move
     * method. Also it traces the output, setpoint, and Encoder Ticks
     */
    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "EncoderDrivetrain", new TracePair("Output", output),
          new TracePair("Setpoint", pidMultiton.getSetpoint()), new TracePair("EncoderTicks", encoder.pidGet()));
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