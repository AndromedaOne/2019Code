package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.telemetries.Trace;
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
    super.absoluteTolerance = 3;
    super.p = 0;
    super.i = 0;
    super.d = 0;
    super.subsystemName = "EncoderPIDHeader";
    super.pidName = "EncoderPID";

    encoder = Robot.drivetrainLeftRearEncoder;
    super.trace = Trace.getInstance();
    encoder.putSensorOnLiveWindow(super.subsystemName, "LeftRearEncoder");
    encoderPIDOut = new EncoderPIDOut();
    pidConfiguration = new PIDConfiguration();
    super.setPIDConfiguration(pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(encoder, encoderPIDOut, pidConfiguration);
  }

  private class EncoderPIDOut implements PIDOutput {
    /**
     * Sets the pidWrite to write all of the PID's output to the drivetrain move
     * method. Also it traces the output, setpoint, and Encoder Ticks
     */
    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "Encoder Drivetrain", new TracePair("Output", output), new TracePair("Setpoint", _setpoint),
          new TracePair("EncoderTicks", encoder.getDistanceTicks()));
      Robot.driveTrain.move(output, 0);
    }
  }

  /**
   * Gets the instance of DrivetrainEncoderPIDController
   * 
   * @return instance
   */
  public static DrivetrainEncoderPIDController getInstance() {
    System.out.println(" --- Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new DriveTrain Encoder PID Controller");
      instance = new DrivetrainEncoderPIDController();
    }
    return instance;
  }

}