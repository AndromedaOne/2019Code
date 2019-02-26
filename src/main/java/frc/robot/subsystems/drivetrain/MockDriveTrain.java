package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MockDriveTrain extends DriveTrain {

  @Override
  protected void initDefaultCommand() {
    System.out.println("DriveTrain initDefaultCommand");
  }

  @Override
  public void move(double forwardBackSpeed, double rotateAmount) {
    // System.out.println("Moving at" + forwardBackSpeed / 100 + "% and rotating at"
    // + rotateAmount / 100 + "%");
  }

  @Override
  public void stop() {
    System.out.println("DriveTrain Stop");
  }

  @Override
  public WPI_TalonSRX getLeftRearTalon() {
    return null;
  }

  public void shiftToLowGear() {

  }

  @Override
  public void shiftToHighGear() {

  }

  @Override
  public boolean getShifterPresentFlag() {
    return false;
  }

}