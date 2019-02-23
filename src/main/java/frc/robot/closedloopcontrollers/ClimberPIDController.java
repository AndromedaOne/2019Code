package frc.robot.closedloopcontrollers;

public class ClimberPIDController extends PIDControllerBase {

    public ClimberPIDController() {
        super.absoluteTolerance = 0;
        super.p = 0;
        super.i = 0;
        super.d = 0;
        super.subsystemName = "ClimberUltrasonic";
        super.pidName = "ClimberPID";
    }

}