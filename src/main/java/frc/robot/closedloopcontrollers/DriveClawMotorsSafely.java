package frc.robot.closedloopcontrollers;

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

  /**
   * Drives the claw intake motors safely per the specifications
   * 
   * @param speed The speed to drive the claw at
   */
  public static void DriveClawMotors(double speed) {
    conf = Robot.getConfig();
    iSensor = Robot.clawInfraredSensor;

    int threshold = conf.getInt("ports.claw.infrared.threshold");
    if (iSensor.getInfraredDistance() >= threshold) {
      Robot.claw.driveIntakeMotors(speed);
      hasBall = true;
    } else {
      Robot.claw.stop();
      hasBall = false;
    }

  }

}