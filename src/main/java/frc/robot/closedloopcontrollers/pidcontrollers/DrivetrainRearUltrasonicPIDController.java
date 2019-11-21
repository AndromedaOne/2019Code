package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.*;
import frc.robot.sensors.ultrasonicsensor.UltrasonicSensor;

public class DrivetrainRearUltrasonicPIDController extends PIDControllerBase {

  private static DrivetrainRearUltrasonicPIDController instance;
  private UltrasonicPIDOut ultrasonicPIDOut;
  private UltrasonicSensor ultrasonic;

  /**
   * Sets the PID variables and absolute Tolerance; all other PID parameters are
   * set in the PIDController Base class Sets the ultrasonic, ultrasonicPIDOut,
   * trace, and pidConfiguration variables. Also creates the ultrasonicPID from
   * the PIDMultiton class.
   */
  private DrivetrainRearUltrasonicPIDController() {
    double absoluteTolerance = 3;
    double p = 0;
    double i = 0;
    double d = 0;
    String subsystemName = "RearUltrasonicPIDHeader";
    String pidName = "RearUltrasonicPID";

    PIDConfiguration pidConfiguration = new PIDConfiguration(p, i, d, 0, 0, 1, 1,
     subsystemName, pidName);

    ultrasonic = Robot.drivetrainFrontUltrasonic;
    ultrasonic.putSensorOnLiveWindow(subsystemName, "RearUltrasonic");
    ultrasonicPIDOut = new UltrasonicPIDOut();
    PIDMultiton.getInstance(ultrasonic, ultrasonicPIDOut, pidConfiguration);
  }

  private class UltrasonicPIDOut implements PIDOutput {

    /**
     * Sets the pidWrite to write all of the PID's output to the drivetrain move
     * method. Also it traces the output, setpoint, and distance in inches
     */
    @Override
    public void pidWrite(double output) {
      Robot.gyroCorrectMove.moveUsingGyro(-output, 0, false, false);
    }

  }

  /**
   * Gets the instance of DriveTrainUltrasonicPIDController
   * 
   * @return instance
   */
  public static DrivetrainRearUltrasonicPIDController getInstance() {
    System.out.println(" ---Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating New Drivetrain Rear Ultrasonic PID Controller");
      instance = new DrivetrainRearUltrasonicPIDController();
    }
    return instance;
  }
}