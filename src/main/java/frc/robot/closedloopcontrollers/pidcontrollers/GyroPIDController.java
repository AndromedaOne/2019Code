package frc.robot.closedloopcontrollers.pidcontrollers;

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
  private GyroPIDController() {
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
    super.setPIDConfiguration(super.pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(navXGyroSensor, gyroPIDOut, super.pidConfiguration);
  }

  private class GyroPIDOut implements PIDOutput {

    /**
     * Sets the pidWrite to write all of the PID's output to the drivetrain move
     * method. Also it traces the output, setpoint, and angle
     */
    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "GyroPid", new TracePair("Output", output),
          new TracePair("Setpoint", pidMultiton.getSetpoint()), new TracePair("DistanceInches", navXGyroSensor.pidGet()));

      Robot.gyroCorrectMove.moveUsingGyro(0, output, false, false);
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