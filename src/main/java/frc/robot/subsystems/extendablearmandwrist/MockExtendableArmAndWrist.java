package frc.robot.subsystems.extendablearmandwrist;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MockExtendableArmAndWrist extends ExtendableArmAndWrist {

  @Override
  public void moveArmWrist(double extensionSpeed, double wristRotSpeed, double shoulderRotSpeed) {
    // System.out.println("Extension speed is " + extensionSpeed);
    // System.out.println("Wrist rotate speed is " + wristRotSpeed);
    // System.out.println("Shoulder rotate speed is " + shoulderRotSpeed);
  }

  @Override
  public WPI_TalonSRX getTopExtendableArmAndWristTalon() {
    return null;
  }

  @Override
  public WPI_TalonSRX getBottomExtendableArmAndWristTalon() {
    return null;
  }

  @Override
  public WPI_TalonSRX getShoulderJointTalon() {
    return null;
  }

}