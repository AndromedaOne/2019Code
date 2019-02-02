package frc.robot.subsystems.extendablearmandwrist;

public class MockExtendableArmAndWrist extends ExtendableArmAndWrist {

  @Override
  public void goToHeight(EnumHatchOrCargo hatchOrCargo, EnumArmLevel armLevel) {
    System.out.println("MockExtendableArmAndWrist.goToHeight:armLevel: " + armLevel);
    System.out.println("MockExtendableArmAndWrist.goToHeight:hatchOrCargo: " + hatchOrCargo);
  }

  @Override
  public void stow() {

  }

  @Override
  public void move(double forwardBackSpeed, double rotateAmount) {
    System.out.println("MockExtendableArmAndWrist.move:forwardBackSpeed: " + forwardBackSpeed);
    System.out.println("MockExtendableArmAndWrist.move:rotateAmount: " + rotateAmount);
  }

  @Override
  public void shoulderRotate(double rotateAmount) {
    System.out.println("MockExtendableArmAndWrist.shoulderRotate:rotateAmount: " + rotateAmount);
  }

  @Override
  public boolean moveIsDone() {
    return true;
  }

}