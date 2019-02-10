package frc.robot.closedloopcontrollers;

import com.typesafe.config.Config;

import frc.robot.Robot;
import frc.robot.sensors.infrareddistancesensor.InfraredDistanceSensor;
import frc.robot.sensors.infrareddistancesensor.MockInfraredDistanceSensor;
import frc.robot.sensors.infrareddistancesensor.RealInfraredDistanceSensor;

public class DriveClawMotorsSafely {

  static Config conf;
  static InfraredDistanceSensor iSensor;

  /**
   * Drives the claw intake motors safely per the specifications
   * 
   * @param speed The speed to drive the claw at
   */
  public static void DriveClawMotors(double speed) {
    conf = Robot.getConfig();
    if (conf.hasPath("ports.claw.infrared")) {
      iSensor = new RealInfraredDistanceSensor(conf.getInt("ports.claw.infrared.port"));
    } else {
      iSensor = new MockInfraredDistanceSensor();
    }

    int threshold = conf.getInt("ports.claw.infrared.threshold");
    if (iSensor.getInfraredDistance() >= threshold) {
      Robot.claw.driveIntakeMotors(speed);
    }

  }

}
