package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.sensors.NavXGyroSensor;

public class RaiseRobot extends Command {

    private NavXGyroSensor gyro = NavXGyroSensor.getInstance();
    private double initialXValue = 0;
    private double initialYValue = 0;
    private double p = 0;

    public void initialize() {
        initialXValue = gyro.getXAngle();
        initialYValue = gyro.getYAngle();
    }

    public void execute() {

        double angleY = gyro.getYAngle() - initialYValue;
        double angleX = gyro.getXAngle() - initialXValue;

        //Left Front Solenoid
        if((angleY < 0) || (angleX < 0)) {
            error = p * (Math.abs(angleY) + Math.abs(angleX));
        }

        // Left Rear Solenoid
        if((angleY > 0 ) || (angleX < 0)) {
            error = p * (angleY + Math.abs(angleX));
        }

        // Right Front Solenoid
        if ((angleY < 0) || (angleX > 0)) {
            error = p * (Math.abs(angleY) + angleX);
        }

        // Right Rear Solenoid
        if ((angleY > 0) || (angleX > 0)) {
            error = p * (angleY + angleX);
        }
    }

    

    @Override
    protected boolean isFinished() {
        return false;
    }

    public void end() {   

    }

}