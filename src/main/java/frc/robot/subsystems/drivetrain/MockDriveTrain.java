package frc.robot.subsystems.drivetrain;

public class MockDriveTrain extends DriveTrain {

  @Override
  protected void initDefaultCommand() {
    System.out.println("DriveTrain initDefaultCommand");
  }

  @Override
  public void move(double forwardBackSpeed, double rotateAmount) {
    //System.out.println("Moving at" + forwardBackSpeed / 100 + "% and rotating at" + rotateAmount / 100 + "%");
  }

  @Override
  public void stop() {
    System.out.println("DriveTrain Stop");
  }

}