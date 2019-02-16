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

    double threshold = conf.getDouble("ports.claw.infrared.threshold");
    if (iSensor.getInfraredDistance() >= threshold) {
      if(speed >= 0) {
        Robot.claw.stop();
      }else {
        Robot.claw.driveIntakeMotors(speed);
      }
      hasBall = true;
    } else {
      Robot.claw.driveIntakeMotors(speed);
      hasBall = false;
    }

  }

}
