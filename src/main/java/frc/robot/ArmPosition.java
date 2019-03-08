package frc.robot;

public class ArmPosition {
    private double shoulderAngle = 0;
    private double armRetraction = 0;
    private double wristAngle = 0;
    
    public ArmPosition(double shoulderAngleParam, double armRetractionParam, double wristAngleParam) {
        shoulderAngle = shoulderAngleParam;
        armRetraction = armRetractionParam;
        wristAngle = wristAngleParam;
    }

    public double getShoulderAngle() {
        return shoulderAngle;
    }

    public double getArmRetraction() {
        return armRetraction;
    }

    public double getWristAngle() {
        return wristAngle;
    }

}

