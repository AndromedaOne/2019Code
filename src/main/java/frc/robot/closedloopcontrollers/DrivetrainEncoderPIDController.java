package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class DrivetrainEncoderPIDController {

  private static DrivetrainEncoderPIDController instance;
  private static Trace trace;
  private PIDMultiton encoderPID;
  private EncoderPIDOut encoderPIDOut;
  private MagEncoderSensor encoder;
  private double outputRange = 1;
  private double absoluteTolerance;
  private double _setpoint;
  private PIDConfiguration pidConfiguration;
  private final double p = 0;
  private final double i = 0;
  private final double d = 0;
  // I did not add an F variable because we have yet to use it

  /**
   * Sets the encoder, encoderPIDOut, trace, and pidConfiguration variables. 
   * Also creates the encoderPID from the PIDMultiton class.
   */
  private DrivetrainEncoderPIDController() {
    encoder = Robot.drivetrainLeftRearEncoder;
    trace = Trace.getInstance();
    encoder.putOnLiveWindow("DriveTrain", "LeftRearEncoder");
    encoderPIDOut = new EncoderPIDOut();
    pidConfiguration = new PIDConfiguration();
    setPIDConfiguration(pidConfiguration);
    encoderPID = PIDMultiton.getInstance(encoder, encoderPIDOut, 
    pidConfiguration);
  }

  
  private class EncoderPIDOut implements PIDOutput {
  /**
   * Sets the pidWrite to write all of the PID's output to the drivetrain move 
   * method. Also it traces the output, setpoint, and Encoder Ticks
   */
    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "Encoder Drivetrain", 
      new TracePair("Output", output), 
      new TracePair("Setpoint", _setpoint),
      new TracePair("EncoderTicks", encoder.getDistanceTicks()));
      Robot.drivetrain.move(output, 0);
    }
  }

  /**
   * Gets the instance of DrivetrainEncoderPIDController
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

  /**
   * This method takes in a setpoint in ticks to move the robot using the 
   * encoder PID
   * 
   * @param setpoint
   */
  public void setSetpoint(double setpoint) {
    _setpoint = setpoint;
    encoderPID.setSetpoint(setpoint + encoder.getDistanceTicks());
  }

  /**
   * enables the encoderPID
   */
  public void enable() {
    encoderPID.enable();
  }

  /**
   * resets the encoderPID
   */
  public void reset() {
    encoderPID.reset();
  }

  /**
   * stops the EncoderPID
   */
  public void stop() {
    encoderPID.stop();
  }

  /**
   * @return true if the EncoderPID is on target
   */
  public boolean isDone() {
    return encoderPID.isDone();
  }

  /**
   * takes a pidConfiguration and sets all of its member variables to that of 
   * DrivetrainEncoderPIDController
   * It sets:
   * p,i,d, absoluteTolerace, maxOutput, minOutput, and liveWindowName
   */
  private void setPIDConfiguration(PIDConfiguration pidConfiguration) {
    pidConfiguration.setP(p);
    pidConfiguration.setI(i);
    pidConfiguration.setD(d);
    pidConfiguration.setAbsoluteTolerance(absoluteTolerance);
    pidConfiguration.setMaximumOutput(outputRange);
    pidConfiguration.setMinimumOutput(-outputRange);
    pidConfiguration.setLiveWindowName("DriveTrain");
  }
}