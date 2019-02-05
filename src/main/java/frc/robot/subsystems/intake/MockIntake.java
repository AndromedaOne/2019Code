package frc.robot.subsystems.intake;

public class MockIntake extends Intake {
  protected IntakeArmPositionsEnum position = IntakeArmPositionsEnum.UNKNOWN;

  @Override
  public void rollIntake(double speed) {
    System.out.println("trying to roll intake at " + speed);
  }

  @Override
  public void moveIntakeArm(double speed) {
    System.out.println("trying to move intake at " + speed);
  }

  @Override
  protected void initDefaultCommand() {

  }

  @Override
  public IntakeArmPositionsEnum getCurrentIntakeArmPosition() {
    return position;
  }

  @Override
  public void setCurrentIntakeArmPosition(IntakeArmPositionsEnum position) {
    this.position = position;
    System.out.println("Setting Position to " + position);
  }

}