package frc.robot.subsystems.extendablearmandwrist;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import frc.robot.Robot;

public class RealExtendableArmAndWrist extends ExtendableArmAndWrist {
  private static RealExtendableArmAndWrist instance;
  private WPI_TalonSRX shoulderJointTalon;
  private WPI_TalonSRX topExtendableArmAndWristTalon;
  private WPI_TalonSRX bottomExtendableArmAndWristTalon;

  public WPI_TalonSRX getShoulderJointTalon() {
    return shoulderJointTalon;
  }

  public WPI_TalonSRX getTopExtendableArmAndWristTalon() {
    return topExtendableArmAndWristTalon;
  }

  public WPI_TalonSRX getBottomExtendableArmAndWristTalon() {
    return bottomExtendableArmAndWristTalon;
  }

  /**
   * Creates all of the talons for the arm, wrist, and shoulder joint
   */
  private RealExtendableArmAndWrist() {
    Config armConf = Robot.getConfig().getConfig("ports.armAndWrist");
    shoulderJointTalon = new WPI_TalonSRX(armConf.getInt("shoulderJointTalon"));
    topExtendableArmAndWristTalon = new WPI_TalonSRX(armConf.getInt("topExtendableArmAndWristTalon"));
    bottomExtendableArmAndWristTalon = new WPI_TalonSRX(armConf.getInt("bottomExtendableArmAndWristTalon"));
  }

  /**
   * @return the instance
   */
  public static RealExtendableArmAndWrist getInstance() {
    if (instance == null) {
      instance = new RealExtendableArmAndWrist();
    }
    return instance;
  }

  /**
   * @param hatchOrCargo Whether or not the robot is carrying a hatch panel or a
   * cargo ball
   * @param armLevel the level to raise the arm to
   */
  public void goToHeight(EnumHatchOrCargo hatchOrCargo, EnumArmLevel armLevel) {

  }

    @Override
    public void goToHeight() {

    }

    @Override
    public void stow() {

    }

}