package frc.robot.groupcommands.autopaths;

import frc.robot.Robot;

public class AutoStartingConfig {
  public static boolean goThroughRobot;
  public static boolean onRightSide;
  public static boolean onLevelTwo;
  public static boolean hasLineFollower = Robot.getConfig().hasPath("sensors.lineFollowSensor.lineFollowSensor4905");
}