package frc.robot.exceptions;

public class ArmOutOfBoundsException extends Exception  {
    
    public ArmOutOfBoundsException(double armPosition, double wristPosition, double shoulderPosition)   {
        super("Arm would've gone to " + armPosition+ "," + wristPosition + "," + shoulderPosition);
    }
}