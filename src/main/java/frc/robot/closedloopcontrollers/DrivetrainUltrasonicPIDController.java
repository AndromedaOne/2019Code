package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.sensors.ultrasonicsensor.UltrasonicSensor;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class DrivetrainUltrasonicPIDController {

  private static DrivetrainUltrasonicPIDController instance;
  private static Trace trace;
  private PIDMultiton ultrasonicPID;
  private UltrasonicPIDOut ultrasonicPIDOut;
  private UltrasonicSensor ultrasonic;
  private double outputRange = 1;
  private double absoluteTolerance;
  private double _setpoint;
  private PIDConfiguration pidConfiguration = new PIDConfiguration();
  private final double p = 0;
  private final double i = 0;
  private final double d = 0;

  /**
   * Sets the ultrasonic, ultrasonicPIDOut, trace, and pidConfiguration 
   * variables. Also creates the ultrasonicPID from the PIDMultiton class.
   */
  public DrivetrainUltrasonicPIDController(UltrasonicSensor ultrasonicParam) {
    ultrasonic = ultrasonicParam;
    trace = Trace.getInstance();
    ultrasonic.putOnLiveWindow("Drivetrain", "Ultrasonic");
    ultrasonicPIDOut = new UltrasonicPIDOut();
    setPIDConfiguration(pidConfiguration);
    ultrasonicPID = PIDMultiton.getInstance(ultrasonic, ultrasonicPIDOut, 
    pidConfiguration);
  }

  private class UltrasonicPIDOut implements PIDOutput {

  /**
   * Sets the pidWrite to write all of the PID's output to the drivetrain move 
   * method. Also it traces the output, setpoint, and distance in inches
   */
    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "Ultrasonic Drivetrain", new TracePair("Output", 
      output),
          new TracePair("Setpoint", _setpoint), new TracePair("DistanceInches", 
          ultrasonic.getDistanceInches()));

      Robot.drivetrain.move(output, 0);
    }

  }

  /**
   * Gets the instance of DriveTrainUltrasonicPIDController
   * @return instance
   */
  public static DrivetrainUltrasonicPIDController getInstance(UltrasonicSensor 
  sensor) {
    System.out.println(" ---Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Drivetrain Ultrasonic PID Controller");
      instance = new DrivetrainUltrasonicPIDController(sensor);
    }
    return instance;
  }

  /** 
   * Sets the setpoint for ultrasonicPID
   */
  public void setSetpoint(double setpoint) {
    ultrasonicPID.setSetpoint(setpoint);
  }

  /**
   * Enables the ultrasonicPID
   */
  public void enable() {
    ultrasonicPID.enable();
  }

  /**
   * Resets the ultrasonicPID
   */
  public void reset() {
    ultrasonicPID.reset();
  }

  /**
   * Stops the ultrasonicPID
   */
  public void stop() {
    ultrasonicPID.stop();
  }

  /**
   * @return true if the ultrasonicPID is on target
   */
  public boolean isDone() {
    return ultrasonicPID.isDone();
  }

  /**
   * takes a pidConfiguration and sets all of its member variables to that of 
   * DrivetrainUltrasonicPIDController
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