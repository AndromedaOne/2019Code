package frc.robot.closedloopcontrollers.pidcontrollers;

import java.util.function.DoubleSupplier;

public class IncrementalPidSetpoint {
  private double finalAngleSetpoint;
  private double previousSetpoint;

  public double getFinalAngleSetpoint() {
    return finalAngleSetpoint;
  }

  private double safeSetpointDelta;
  private DoubleSupplier currentPositionSupplier;

  public IncrementalPidSetpoint(double finalAngleSetpoint, double safeSetpointDelta,
      DoubleSupplier currentPositionSupplier) {
    this.finalAngleSetpoint = finalAngleSetpoint;
    this.safeSetpointDelta = safeSetpointDelta;
    this.currentPositionSupplier = currentPositionSupplier;

    previousSetpoint = Double.NaN;
  }

  public double getSetpoint() {
    if(Double.isNaN(previousSetpoint)){
      previousSetpoint = currentPositionSupplier.getAsDouble();
    }
    double signOfMovement = Math.signum(finalAngleSetpoint - previousSetpoint);

    double setpoint = previousSetpoint + safeSetpointDelta * signOfMovement;

    if (Math.signum(finalAngleSetpoint - setpoint) != signOfMovement) {
      setpoint = finalAngleSetpoint;
    }else {
      // This means that the calculated setpoint is correct
    }
    previousSetpoint = setpoint;
    return setpoint;
  
  }

  public boolean isSetpointFinalSetpoint() {
    return getSetpoint() == finalAngleSetpoint;
  }
}