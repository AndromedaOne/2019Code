package frc.robot.closedloopcontrollers;

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
  private double _maxAllowableDelta;
  private double outputRange = 1;
  private double absoluteTolerance;
  private double _setpoint;
  private PIDConfiguration pidConfiguration;
  private final double p = 0;
  private final double i = 0;
  private final double d = 0;

  public DrivetrainUltrasonicPIDController(UltrasonicSensor ultrasonicParam) {
    ultrasonic = ultrasonicParam;
    trace = Trace.getInstance();
    ultrasonic.putOnSmartDashboard("Drivetrain", "Ultrasonic");

  }

  private class UltrasonicPIDOut implements PIDOutput {

    public UltrasonicPIDOut(double maxAllowableDelta) {
      _maxAllowableDelta = maxAllowableDelta;
    }

    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "Ultrasonic Drivetrain", new TracePair("Output", output),
          new TracePair("Setpoint", _setpoint), new TracePair("DistanceInches", ultrasonic.getDistanceInches()));

      Robot.drivetrain.move(output, 0);
    }

  }

  public static DrivetrainUltrasonicPIDController getInstance(UltrasonicSensor sensor) {
    System.out.println(" ---Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Drivetrain Ultrasonic PID Controller");
      instance = new DrivetrainUltrasonicPIDController(sensor);
    }
    return instance;
  }

  public void setSetpoint(double setpoint) {

  }

  public PIDMultiton getUltrasonicPiD() {
    return ultrasonicPID;
  }
}