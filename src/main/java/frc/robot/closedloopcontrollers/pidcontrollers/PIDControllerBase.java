package frc.robot.closedloopcontrollers.pidcontrollers;

public class PIDControllerBase {
  public PIDMultiton pidMultiton;
  protected double outputRange = 1;

  protected PIDConfiguration pidConfiguration = new PIDConfiguration();

  protected double pForMovingQuickly = 0;
  protected double iForMovingQuickly = 0;
  protected double dForMovingQuickly = 0;
  protected double absoluteToleranceForQuickMovement = 0;

  protected double pForMovingPrecisely = 0;
  protected double iForMovingPrecisely = 0;
  protected double dForMovingPrecisely = 0;
  protected double absoluteToleranceForPreciseMovement = 0;

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
  public void setPIDConfiguration(PIDConfiguration pidConfiguration) {
    pidConfiguration.setP(pForMovingQuickly);
    pidConfiguration.setI(iForMovingQuickly);
    pidConfiguration.setD(dForMovingQuickly);
    pidConfiguration.setAbsoluteTolerance(absoluteToleranceForQuickMovement);
    pidConfiguration.setMaximumOutput(outputRange);
    pidConfiguration.setMinimumOutput(-outputRange);
    pidConfiguration.setLiveWindowName(subsystemName);
    pidConfiguration.setPIDName(pidName);
  }

  public double getPForMovingQuickly() {
    return pForMovingQuickly;
  }

  public double getIForMovingQuickly() {
    return iForMovingQuickly;
  }

  public double getDForMovingQuickly() {
    return dForMovingQuickly;
  }

  public double getToleranceForMovingQuickly() {
    return absoluteToleranceForQuickMovement;
  }

  public double getPForMovingPrecisely() {
    return pForMovingPrecisely;
  }

  public double getIForMovingPrecisely() {
    return iForMovingPrecisely;
  }

  public double getDForMovingPrecisely() {
    return dForMovingPrecisely;
  }

  public double getToleranceForMovingPrecisely() {
    return absoluteToleranceForPreciseMovement;
  }

}