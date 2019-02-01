package frc.robot.subsystems.extendablearmandwrist;

public class MockExtendableArmAndWrist extends ExtendableArmAndWrist {

  @Override
  public void goToHeight(EnumHatchOrCargo hatchOrCargo, EnumArmLevel armLevel) {

  }

  @Override
  public void stow() {

  }

  @Override
  public void move(double forwardBackSpeed, double rotateAmount) {
    System.out.println(forwardBackSpeed);
    System.out.println(rotateAmount);
  }

}