package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class GyroPIDController extends PIDControllerBase {
  private static GyroPIDController instance;
  private NavXGyroSensor navXGyroSensor;
  private GyroPIDOut gyroPIDOut;

  /**
   * Sets the PID variables and absolute Tolerance; all other PID parameters are
   * set in the PIDController Base class Sets the NavXGyroSensor, gyroPIDOut,
   * trace, and pidConfiguration variables. Also creates the gyroPID from the
   * PIDMultiton class.
   */
  public GyroPIDController() {
    super.absoluteTolerance = 3;
    super.p = 0;
    super.i = 0;
    super.d = 0;
    super.subsytemName = "GyroPIDHeader";
    super.pidName = "GyroPID";

    navXGyroSensor = NavXGyroSensor.getInstance();
    super.trace = Trace.getInstance();
    gyroPIDOut = new GyroPIDOut();
    navXGyroSensor.putSensorOnLiveWindow(super.subsytemName, "Gyro");
    super.setPIDConfiguration(pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(navXGyroSensor, gyroPIDOut, pidConfiguration);
  }

  private class GyroPIDOut implements PIDOutput {

    /**
     * Sets the pidWrite to write all of the PID's output to the drivetrain move
     * method. Also it traces the output, setpoint, and angle
     */
    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "Ultrasonic Drivetrain", new TracePair("Output", output),
          new TracePair("Setpoint", _setpoint), new TracePair("DistanceInches", navXGyroSensor.getZAngle()));

      Robot.driveTrain.move(0, output);
    }

  }

  /**
   * Gets the instance of GyroPIDController
   * 
   * @return instance
   */
  public static GyroPIDController getInstance() {
    System.out.println(" ---Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Drivetrain Gyro PID Controller");
      instance = new GyroPIDController();
    }
    return instance;
  }
}