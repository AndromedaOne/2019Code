package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MockDriveTrain extends DriveTrain {

  @Override
  protected void initDefaultCommand() {
    // System.out.println("DriveTrain initDefaultCommand");
  }

  @Override
  public void move(double forwardBackSpeed, double rotateAmount, boolean squaredInputs, boolean useAccelLimits) {
    // System.out.println("Moving at" + forwardBackSpeed / 100 + "% and rotating at"
    // + rotateAmount / 100 + "%");
  }

  @Override
  public void stop() {
    // System.out.println("DriveTrain Stop");
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

  @Override
  public void changeControlMode(NeutralMode mode) {
    // System.out.println("Changed mode to: " + mode.toString());
  }

  @Override
  public void setGear(RobotGear gear) {

  }

  @Override
  public RobotGear getGear() {
    return RobotGear.SLOWLOWGEAR;
  }

  @Override
  public void toggleSlowMode() {
    System.out.println("Toggle Slow Mode");
  }

  @Override
  public void toggleShifter() {
    System.out.println("Toggle Shifter");
  }
}