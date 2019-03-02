package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.telemetries.Trace;

public class PIDControllerBase implements Sendable {
  protected static Trace trace;
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

  @Override
  public String getName() {
    return null;
  }

  @Override
  public void setName(String name) {

  }

  @Override
  public String getSubsystem() {
    return null;
  }

  @Override
  public void setSubsystem(String subsystem) {

  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("PIDController");
    builder.setSafeState(this.pidMultiton.pidController::reset);
    builder.addDoubleProperty("p", this.pidMultiton.pidController::getP, this.pidMultiton.pidController::setP);
    builder.addDoubleProperty("i", this.pidMultiton.pidController::getI, this.pidMultiton.pidController::setI);
    builder.addDoubleProperty("d", this.pidMultiton.pidController::getD, this.pidMultiton.pidController::setD);
    builder.addDoubleProperty("f", this.pidMultiton.pidController::getF, this.pidMultiton.pidController::setF);
    builder.addDoubleProperty("setpoint", this.pidMultiton.pidController::getSetpoint, this::setSetpoint);
    builder.addBooleanProperty("enabled", this.pidMultiton.pidController::isEnabled, this.pidMultiton.pidController::setEnabled);
  }

}