package frc.robot.closedloopcontrollers;

import frc.robot.telemetries.Trace;

public class PIDControllerBase {
  protected static Trace trace;
  protected PIDMultiton pidMultiton;
  protected double outputRange = 1;
  protected double absoluteTolerance = 0;
  protected double _setpoint = 0;
  protected PIDConfiguration pidConfiguration = new PIDConfiguration();
  protected double p = 0;
  protected double i = 0;
  protected double d = 0;
  protected String subsystemName;
  protected String pidName;

  /**
   * Sets the setpoint for pidController
   */
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint);
  }

  /**
   * Enables the pidController
   */
  public void enable() {
    pidMultiton.enable();
  }

  /**
   * Resets the pidController
   */
  public void reset() {
    pidMultiton.reset();
  }

  /**
   * Stops the pidController
   */
  public void disable() {
    pidMultiton.disable();
  }

  /**
   * @return true if the pidController is on target
   */
  public boolean onTarget() {
    return pidMultiton.onTarget();
  }

  /**
   * takes a pidConfiguration and sets all of its member variables to that of
   * DrivetrainUltrasonicPIDController It sets: p,i,d, absoluteTolerace,
   * maxOutput, minOutput, and liveWindowName
   */
  protected void setPIDConfiguration(PIDConfiguration pidConfiguration) {
    pidConfiguration.setP(p);
    pidConfiguration.setI(i);
    pidConfiguration.setD(d);
    pidConfiguration.setAbsoluteTolerance(absoluteTolerance);
    pidConfiguration.setMaximumOutput(outputRange);
    pidConfiguration.setMinimumOutput(-outputRange);
    pidConfiguration.setLiveWindowName(subsystemName);
    pidConfiguration.setPIDName(pidName);
  }
}