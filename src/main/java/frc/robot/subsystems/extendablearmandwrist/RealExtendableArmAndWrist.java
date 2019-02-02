package frc.robot.subsystems.extendablearmandwrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.commands.TeleopArm;

public class RealExtendableArmAndWrist extends ExtendableArmAndWrist {
  private static RealExtendableArmAndWrist instance;
  private WPI_TalonSRX shoulderJointTalon;
  private WPI_TalonSRX topExtendableArmAndWristTalon;
  private WPI_TalonSRX bottomExtendableArmAndWristTalon;
  protected DifferentialDrive differentialDrive;

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
    differentialDrive = new DifferentialDrive(topExtendableArmAndWristTalon, bottomExtendableArmAndWristTalon);
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

  @Override
  public void goToHeight(EnumHatchOrCargo hatchOrCargo, EnumArmLevel armLevel) {

  }

  @Override
  public void stow() {

  }

  // TODO: Fill these out
  /**
   * @param forwardBackSpeed
   * @param rotateAmount
   */
  @Override
  public void move(double forwardBackSpeed, double rotateAmount) {
    differentialDrive.arcadeDrive(forwardBackSpeed, rotateAmount);
  }

  @Override
  public void shoulderRotate(double rotateAmount) {
    shoulderJointTalon.set(ControlMode.PercentOutput, rotateAmount);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new TeleopArm());
  }

}