package frc.robot.subsystems.pneumaticstilts;

public class MockPneumaticStilts extends PneumaticStilts {
  public void stabilizedMove(double frontLeftLeg, double frontRightLeg, double rearLeftLeg, double rearRightLeg) {
    System.out.println("Moving Legs (FL, FR, RL, RR) : " + frontLeftLeg + " " + frontRightLeg + " " + rearLeftLeg + " "
        + rearRightLeg);
  }

  public MockPneumaticStilts() {
    System.out.println("Using Mock Pneumatic Stilt Subsystem");
  }
  
  public void stopAllLegs() {
    System.out.println("Stopping all legs.");
  }

  public void extendFrontLegs() {
    System.out.println("Extending Front Stilts Solenoid");
  }

  public void extendRearLegs() {
    System.out.println("Extending Rear Stilts Solenoid");
  }

  public void stopFrontLegs() {
    System.out.println("Stopped Front Stilts Solenoid");
  }

  public void stopRearLegs() {
    System.out.println("Stopped Rear Stilts Solenoid");
  }

  @Override
  public void retractFrontLegs() {
    System.out.println("Retracting Front Stilts Solenoid");
  }

  @Override
  public void retractRearLegs() {
    System.out.println("Retracting Rear Stilts Solenoid");
  }
}