package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.subsystems.pneumaticstilts.PneumaticStilts;
import frc.robot.Robot;

public class RaiseRobot extends Command {

    private NavXGyroSensor gyro = NavXGyroSensor.getInstance();
    private PneumaticStilts pneumaticStilts = Robot.getPneumaticStilts();
    private double initialXValue = 0;
    private double initialYValue = 0;
    private double frontLeftError = 0;
    private double frontRightError = 0;
    private double rearLeftError = 0;
    private double rearRightError = 0;
    private double p = 0;

    public void initialize() {
        initialXValue = gyro.getXAngle();
        initialYValue = gyro.getYAngle();
    }

    public void execute() {

        double angleY = gyro.getYAngle() - initialYValue;
        double angleX = gyro.getXAngle() - initialXValue;

        //Left Front Leg
        if((angleY < 0) || (angleX < 0)) {
            frontLeftError =  1 - (p * (Math.abs(angleY) + Math.abs(angleX)));
        }

        // Left Rear Leg
        if((angleY > 0 ) || (angleX < 0)) {
            rearLeftError = 1 - (p * (angleY + Math.abs(angleX)));
        }

        // Right Front Leg
        if ((angleY < 0) || (angleX > 0)) {
            frontRightError = 1 - (p * (Math.abs(angleY) + angleX));
        }

        // Right Rear Leg
        if ((angleY > 0) || (angleX > 0)) {
            rearRightError = 1 - (p * (angleY + angleX));
        }

        pneumaticStilts.move(frontLeftError, frontRightError,
        rearLeftError, rearRightError);

    }

    

    @Override
    protected boolean isFinished() {
        return false;
    }

    public void end() {   

    }

}