package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.pidcontrollers.DrivetrainUltrasonicPIDController;;

public class MoveUsingBackUltrasonic extends Command {
    private int setpoint = 0;

    /**
     * Takes a Setpoint in inches This is the distance you will be from the wall
     */
    public MoveUsingBackUltrasonic(int distanceFromWallInInches) {
        setpoint = distanceFromWallInInches;
    }

    public void initialize() {
        System.out.println(" -- Moving With Ultrasonic -- ");
        //frontUltrasonic.setRelativeSetpoint(setpoint);
        //frontUltrasonic.enable();
    }

    public boolean isFinished() {
        return false;//frontUltrasonic.onTarget();
    }

    public void end() {
        //frontUltrasonic.disable();
    }

    public void interrupt() {
        end();
    }
}
