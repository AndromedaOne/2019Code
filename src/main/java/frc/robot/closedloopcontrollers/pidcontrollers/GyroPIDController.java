package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.*;
import frc.robot.sensors.NavXGyroSensor;

public class GyroPIDController {
  private static GyroPIDController instance;
  private NavXGyroSensor navXGyroSensor;
  private GyroPIDOut gyroPIDOut;
  private double kMinOut = 0.0625;
  private PIDMultiton pidMultiton;

  /**
   * Sets the PID variables and absolute Tolerance; all other PID parameters are
   * set in the PIDController Base class Sets the NavXGyroSensor, gyroPIDOut,
   * trace, and pidConfiguration variables. Also creates the gyroPID from the
   * PIDMultiton class.
   */
  private GyroPIDController() {
    double absoluteTolerance = 1;
    double p = 0.011;
    double i = 0.0;
    double d = 0;

    double outputRange = 1;
    String subsystemName = "GyroPIDHeader";
    String pidName = "GyroPID";

    PIDConfiguration pidConfiguration = new PIDConfiguration(p, i, d, 0, 0, 1, absoluteTolerance, subsystemName,
        pidName);

    pidMultiton = PIDMultiton.getInstance(navXGyroSensor, gyroPIDOut, pidConfiguration);

    navXGyroSensor = NavXGyroSensor.getInstance();
    gyroPIDOut = new GyroPIDOut();
    navXGyroSensor.putSensorOnLiveWindow(subsystemName, "Gyro");
  }

  private class GyroPIDOut implements PIDOutput {

    /**
     * Sets the pidWrite to write all of the PID's output to the drivetrain move
     * method. Also it traces the output, setpoint, and angle
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
        output = 0;
      }

      Robot.gyroCorrectMove.moveUsingGyro(0, output, false, false);
    }

  }

  /**
   * Gets the instance of GyroPIDController
   * 
   * @return instance
   */
  public static GyroPIDController getInstance() {
    System.out.println(" ---Asking for Gyro PID Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Gyro PID Controller");
      instance = new GyroPIDController();
    }
    return instance;
  }

  public PIDMultiton getPIDMultiton() {
    return pidMultiton;
  }

}