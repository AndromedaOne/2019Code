package frc.robot.subsystems.extendablearmandwrist;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.TeleopArm;

public abstract class ExtendableArmAndWrist extends Subsystem {

  /**
   * Sets the default command to the teleoperated control command for the arm
   */
  @Override
  protected void initDefaultCommand() {
    // need to create a default command and add it here
    setDefaultCommand(new TeleopArm());
  }

  public abstract void moveArmWrist(double extensionSpeed, double wristRotSpeed, double shoulderRotSpeed);

  public abstract WPI_TalonSRX getTopExtendableArmAndWristTalon();

  public abstract WPI_TalonSRX getBottomExtendableArmAndWristTalon();

  public abstract WPI_TalonSRX getShoulderJointTalon();

}