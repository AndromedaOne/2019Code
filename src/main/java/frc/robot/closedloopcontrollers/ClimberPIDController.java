package frc.robot.closedloopcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainUltrasonicPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.PIDControllerBase;
import frc.robot.closedloopcontrollers.pidcontrollers.PIDMultiton;
import frc.robot.sensors.ultrasonicsensor.UltrasonicSensor;

public class ClimberPIDController extends PIDControllerBase {

  UltrasonicSensor ultrasonicSensor;
  private static DrivetrainUltrasonicPIDController instance;
  private UltrasonicPIDOut ultrasonicPIDOut;

  public ClimberPIDController() {
    super.absoluteTolerance = 0;
    super.p = 0;
    super.i = 0;
    super.d = 0;
    super.subsystemName = "ClimberUltrasonic";
    super.pidName = "ClimberPID";
    ultrasonicSensor = Robot.climbUltrasonicSensor;
    ultrasonicSensor.putSensorOnLiveWindow(super.subsystemName, "ultrasonic");
    ultrasonicPIDOut = new UltrasonicPIDOut();
    super.setPIDConfiguration(pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(ultrasonicSensor, ultrasonicPIDOut, pidConfiguration);
  }

  private class UltrasonicPIDOut implements PIDOutput {

    @Override
    public void pidWrite(double output) {

    }
  }

}