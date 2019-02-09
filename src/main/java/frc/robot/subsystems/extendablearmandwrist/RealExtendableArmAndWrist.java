package frc.robot.subsystems.extendablearmandwrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.PIDConfiguration;
import frc.robot.closedloopcontrollers.pidcontrollers.PIDMultiton;

public class RealExtendableArmAndWrist extends ExtendableArmAndWrist {
  private static RealExtendableArmAndWrist instance;
  private WPI_TalonSRX shoulderJointTalon;
  private WPI_TalonSRX topExtendableArmAndWristTalon;
  private WPI_TalonSRX bottomExtendableArmAndWristTalon;
  protected DifferentialDrive differentialDrive;
  // TODO: Add real encoders
  private PIDSource topArmAndWristEncoder = null;
  private PIDSource bottomArmAndWristEncoder = null;
  private PIDMultiton topArmPid;
  private PIDMultiton bottomArmPid;

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
    topArmAndWristEncoder = new PIDSource() {

      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {

      }

      @Override
      public double pidGet() {
        return 0;
      }

      @Override
      public PIDSourceType getPIDSourceType() {
        return null;
      }
    };
    bottomArmAndWristEncoder = new PIDSource() {

      @Override
      public void setPIDSourceType(PIDSourceType pidSource) {

      }

      @Override
      public double pidGet() {
        return 0;
      }

      @Override
      public PIDSourceType getPIDSourceType() {
        return null;
      }
    };
    topArmPid = PIDMultiton.getInstance(topArmAndWristEncoder, topExtendableArmAndWristTalon, new PIDConfiguration());
    bottomArmPid = PIDMultiton.getInstance(bottomArmAndWristEncoder, bottomExtendableArmAndWristTalon,
        new PIDConfiguration());

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
    double topSetPoint = 0; // TODO Ugly if/then statement
    double bottomSetPoint = 0;
    topArmPid.setSetpoint(topSetPoint);
    bottomArmPid.setSetpoint(bottomSetPoint);
    topArmPid.enable();
    bottomArmPid.enable();
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
  public boolean moveIsDone() {
    return topArmPid.onTarget() && bottomArmPid.onTarget();
  }

}