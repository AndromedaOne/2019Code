package frc.robot.closedloopcontrollers;

import java.time.Duration;
import java.time.Instant;

import com.typesafe.config.Config;

import frc.robot.Robot;
import frc.robot.sensors.infrareddistancesensor.InfraredDistanceSensor;

/**
 * Drives the claw motors safely
 */
public class DriveClawMotorsSafely {

  static Config conf;
  static InfraredDistanceSensor iSensor;
  public static boolean hasBall = false;
  private static Instant initialTime;
  private static boolean initialTimeSet = false;
  private static final double deltaTimeThreshhold = 40; // in milliseconds

  /**
   * Drives the claw intake motors safely per the specifications
   * 
   * @param speed The speed to drive the claw at
   */
  public static void DriveClawMotors(double speed) {
    conf = Robot.getConfig();
    iSensor = Robot.clawInfraredSensor;

    double threshold = conf.getDouble("ports.claw.infrared.threshold");
    if (iSensor.getInfraredDistance() >= threshold) {
      if(!initialTimeSet && speed >= 0) {
        initialTime = Instant.now();
        initialTimeSet = true;
      }
      if (speed >= 0 && (Duration.between(initialTime, Instant.now()).toMillis() > deltaTimeThreshhold)) {
        Robot.claw.stop();
      } else {
        initialTimeSet = false;
        Robot.claw.driveIntakeMotors(speed);
      }
      hasBall = true;
    } else {
      initialTimeSet = false;
      Robot.claw.driveIntakeMotors(speed);
      hasBall = false;
    }

  }

}
