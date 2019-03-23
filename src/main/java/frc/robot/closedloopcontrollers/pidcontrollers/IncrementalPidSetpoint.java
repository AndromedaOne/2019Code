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
  }

  public double getSetpoint() {
    if(previousSetpoint == null){
      previousSetpoint = currentPositionSupplier.getAsDouble();
    }
    if(previousSetpoint != finalAngleSetpoint){
      double signOfMovement = Math.signum(finalAngleSetpoint - previousSetpoint);

      double setpoint = previousSetpoint + safeSetpointDelta * signOfMovement;

      if (Math.signum(finalAngleSetpoint - setpoint) != signOfMovement) {
        setpoint = finalAngleSetpoint;
      }else if(setpoint == finalAngleSetpoint) {
        setpoint = finalAngleSetpoint;
      }
      previousSetpoint = setpoint;
      return setpoint;
    }else{
      return finalAngleSetpoint;
    }
  }

  public boolean isSetpointFinalSetpoint() {
    return getSetpoint() == finalAngleSetpoint;
  }
}