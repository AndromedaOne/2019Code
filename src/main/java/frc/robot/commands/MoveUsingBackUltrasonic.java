package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainUltrasonicPIDController;;

public class MoveUsingBackUltrasonic extends MoveTurnBase {

    /**
     * Takes a Setpoint in inches This is the distance you will be from the wall
     */
    public MoveUsingBackUltrasonic(int distanceFromWallInInches) {

    }

    public void initialize() {
        // TODO There is no back ultrasonic set up yet
    }
}
