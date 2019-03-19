package frc.robot.closedloopcontrollers.pidcontrollers;

import java.util.function.DoubleSupplier;

import frc.robot.ArmPosition;
import frc.robot.Robot;

public class IncrementalPidSetpoint {
    private double finalAngleSetpoint;
    public double getFinalAngleSetpoint(){return finalAngleSetpoint;}
    private double safeSetpointDelta;
    private DoubleSupplier currentPositionSupplier;

    public IncrementalPidSetpoint(double finalAngleSetpoint, double safeSetpointDelta, DoubleSupplier currentPositionSupplier) {
        this.finalAngleSetpoint = finalAngleSetpoint;
        this.safeSetpointDelta = safeSetpointDelta;
        this.currentPositionSupplier = currentPositionSupplier;
    }

    public double getSetpoint() {
        double currentPosition = currentPositionSupplier.getAsDouble();
        double signOfMovement = Math.signum(finalAngleSetpoint - currentPosition);

        double setpoint = currentPosition + safeSetpointDelta * signOfMovement;
    
        if(Math.signum(finalAngleSetpoint - setpoint) != signOfMovement) {
            setpoint = finalAngleSetpoint;
        }
        return setpoint;
    }

    public boolean isSetpointFinalSetpoint() {
        return getSetpoint() == finalAngleSetpoint;
    }
}