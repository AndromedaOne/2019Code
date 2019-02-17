package frc.robot.subsystems.extendablearmandwrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.sensors.magencodersensor.MagEncoderSensor;
import frc.robot.sensors.magencodersensor.RealMagEncoderSensor;

public class RealExtendableArmAndWrist extends ExtendableArmAndWrist {
  private static RealExtendableArmAndWrist instance;
  public final WPI_TalonSRX shoulderJointTalon;
  public final WPI_TalonSRX topExtendableArmAndWristTalon;
  public final WPI_TalonSRX bottomExtendableArmAndWristTalon;
  protected DifferentialDrive differentialDrive;
  public final MagEncoderSensor shoulderEncoder;
  public final MagEncoderSensor armEncoder1;
  public final MagEncoderSensor armEncoder2;

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
    System.out.print("Creating Arm Talons");
    Config armConf = Robot.getConfig().getConfig("ports.armAndWrist");
    shoulderJointTalon = new WPI_TalonSRX(armConf.getInt("shoulderJointTalon"));
    topExtendableArmAndWristTalon = new WPI_TalonSRX(armConf.getInt("topExtendableArmAndWristTalon"));
    bottomExtendableArmAndWristTalon = new WPI_TalonSRX(armConf.getInt("bottomExtendableArmAndWristTalon"));
    shoulderEncoder = Robot.armRotateEncoder1;
    armEncoder1 = Robot.armExtensionEncoder1;
    armEncoder2 = Robot.armExtensionEncoder2;
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

  @Override
  public void moveArmWrist(double extensionSpeed, double wristRotSpeed, double shoulderRotSpeed) {
    differentialDrive.arcadeDrive(extensionSpeed, wristRotSpeed);
    shoulderJointTalon.set(ControlMode.PercentOutput, shoulderRotSpeed);
  }

}