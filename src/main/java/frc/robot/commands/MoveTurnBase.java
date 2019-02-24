package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainEncoderPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainUltrasonicPIDController;
import frc.robot.closedloopcontrollers.pidcontrollers.GyroPIDController;
import frc.robot.sensors.NavXGyroSensor;

public class MoveTurnBase extends Command {

    private DrivetrainUltrasonicPIDController frontUltrasonic = 
    DrivetrainUltrasonicPIDController.getInstance();
    private DrivetrainEncoderPIDController encoderPID = 
    DrivetrainEncoderPIDController.getInstance();
    private GyroPIDController gyroPID = GyroPIDController.getInstance();

    public MoveTurnBase() {
        requires(Robot.driveTrain);
    }

    /**
     * Takes a Setpoint in inches This is the distance you will be from the wall
     */
    public void moveUsingFrontUltrasonic(double setpoint) {
        System.out.println(" -- Moving With Front Ultrasonic -- ");
        frontUltrasonic.setRelativeSetpoint(setpoint);
        frontUltrasonic.enable();
    }

    /**
     * Takes a Setpoint in inches This is the distance you will be from the wall
     */
    public void moveUsingBackUltrasonic(double setpoint) {
        System.out.println(" -- Moving With Back Ultrasonic -- ");
        //backUltrasonic.setRelativeSetpoint(setpoint);
        //backUltrasonic.enable();
    }

    public void moveUsingEncoderPID(double setpoint) {
        System.out.println(" -- Moving With Encoder -- ");
        encoderPID.setRelativeSetpoint(setpoint);
        encoderPID.enable();
    }

    public void turnToCompassHeading(double heading) {
        double deltaAngle = heading - NavXGyroSensor.getInstance().getCompassHeading();
        System.out.println("Raw Delta Angle: " + deltaAngle);
        // This corrects turn that are over 180
        if (deltaAngle > 180) {
            deltaAngle = -(360 - deltaAngle);
            System.out.println("Angle corrected for shortest method, New Delta: " + deltaAngle);
        } else if (deltaAngle < -180) {
            deltaAngle = 360 + deltaAngle;
            System.out.println("Angle corrected for shortest method, New Delta: " + deltaAngle);
        }

        double setPoint = deltaAngle + NavXGyroSensor.getInstance().getZAngle();

        if (Math.abs(deltaAngle) < gyroPID.getAbsoluteTolerance()) {
            System.out.println("Delta is to small, not moving!");
            setPoint = NavXGyroSensor.getInstance().getZAngle();
        }

        System.out.println(" - Turn to Compass Heading  - ");
        System.out.println("Tolerance: " + gyroPID.getAbsoluteTolerance());
        System.out.println("Heading: " + heading);
        System.out.println("Delta Angle: " + deltaAngle);
        System.out.println("SetPoint: " + setPoint);
        System.out.println("Current Heading: " + NavXGyroSensor.getInstance().getCompassHeading());
        System.out.println("Current Z Angle: " + NavXGyroSensor.getInstance().getZAngle());
        gyroPID.setSetpoint(setPoint);
        gyroPID.enable();
    }

    public void deltaTurn(double setPoint) {
        double currentPosition = NavXGyroSensor.getInstance().getZAngle();
        gyroPID.setSetpoint(currentPosition + setPoint);
        gyroPID.enable();
    }

    public void end() {
        gyroPID.disable();
        encoderPID.disable();
        frontUltrasonic.disable();
        // backUltrasonic.disable();
    }

    public void interrupt() {
        end();
    }

    public boolean onTarget() {
        boolean done = frontUltrasonic.onTarget() || encoderPID.onTarget() ||
        gyroPID.onTarget(); // || backUltrasonic.onTarget();
        return done; 
    }

    @Override
    protected boolean isFinished() {
        return onTarget();
    }

}