package frc.robot.closedloopcontrollers.pidcontrollers;

public class PIDControllerBase {
  public PIDMultiton pidMultiton;
  protected double outputRange = 1;
  protected double absoluteTolerance = 0;
  protected PIDConfiguration pidConfiguration = new PIDConfiguration();
  protected double p = 0;
  protected double i = 0;
  protected double d = 0;
  protected String subsystemName;
  protected String pidName;

  /**
   * Sets the setpoint for pidController
   * 
   * @param setpoint Target to go to
   */
  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint);
  }

  public void setRelativeSetpoint(double delta) {
    pidMultiton.setRelativeSetpoint(delta);
  }

  /**
   * Enables the pidController
   */
  public void enable() {
    pidMultiton.enable();
  }

  public boolean isEnabled() {
    return pidMultiton.isEnabled();
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
   * 
   * @param pidConfiguration The configuration to use
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