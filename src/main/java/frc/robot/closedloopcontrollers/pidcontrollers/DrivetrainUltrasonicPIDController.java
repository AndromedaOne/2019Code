package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.sensors.ultrasonicsensor.UltrasonicSensor;

public class DrivetrainUltrasonicPIDController extends PIDControllerBase {

  private static DrivetrainUltrasonicPIDController instance;
  private UltrasonicPIDOut ultrasonicPIDOut;
  private UltrasonicSensor ultrasonic;

  /**
   * Sets the PID variables and absolute Tolerance; all other PID parameters are
   * set in the PIDController Base class Sets the ultrasonic, ultrasonicPIDOut,
   * trace, and pidConfiguration variables. Also creates the ultrasonicPID from
   * the PIDMultiton class.
   */
  private DrivetrainUltrasonicPIDController() {
    super.absoluteTolerance = 3;
    super.p = 0;
    super.i = 0;
    super.d = 0;
    super.subsystemName = "UltrasonicPIDHeader";
    super.pidName = "UltrasonicPID";

    ultrasonic = Robot.drivetrainFrontUltrasonic;
    ultrasonic.putSensorOnLiveWindow(super.subsystemName, "Ultrasonic");
    ultrasonicPIDOut = new UltrasonicPIDOut();
    super.setPIDConfiguration(super.pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(ultrasonic, ultrasonicPIDOut, super.pidConfiguration);
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
  public static DrivetrainUltrasonicPIDController getInstance() {
    System.out.println(" ---Asking for Instance --- ");
    if (instance == null) {
      System.out.println("Creating new Drivetrain Ultrasonic PID Controller");
      instance = new DrivetrainUltrasonicPIDController();
    }
    return instance;
  }
}