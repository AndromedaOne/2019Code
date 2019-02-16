package frc.robot.closedloopcontrollers.pidcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.*;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class PIDMultiton {

  private static Map<PIDName, PIDMultiton> instances;

  static {
    instances = new HashMap<>();
  }

  public static synchronized PIDMultiton getInstance(PIDSource source, PIDOutput output, PIDConfiguration config) {

    PIDName name = new PIDName(source, output);
    PIDMultiton instance = instances.getOrDefault(name, new PIDMultiton(name, config));
    if (config.equals(instance.gitConfig())) {
      instances.put(name, instance);
      return instance;
    } else {
      throw new IllegalArgumentException("configs much match");
    }

  }

  private static class PIDName {
    private PIDSource source;
    private PIDOutput output;

    public PIDName(PIDSource source, PIDOutput output) {
      this.source = source;
      this.output = output;
    }

    public int hashCode() {
      // you pick a hard-coded, randomly chosen, non-zero, odd number
      // ideally different for each class
      return new HashCodeBuilder(17, 37).append(source).append(output).toHashCode();
    }

    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (obj == this) {
        return true;
      }
      if (obj.getClass() != getClass()) {
        return false;
      }
      PIDName rhs = (PIDName) obj;
      return new EqualsBuilder().appendSuper(super.equals(obj)).append(source, rhs.source).append(output, rhs.output)
          .isEquals();
    }

  }

  private PIDController pidController;
  private PIDConfiguration config;
  private PIDName name;

  private PIDMultiton(PIDName name, PIDConfiguration config) {
    this.name = name;
    this.config = config;
    pidController = new PIDController(config.getP(), config.getI(), config.getD(), name.source, name.output);
    pidController.setOutputRange(config.getMinimumOutput(), config.getMaximumOutput());

    pidController.setAbsoluteTolerance(config.getAbsoluteTolerance());

    if (config.getLiveWindowName() == config.getPIDName()) {
      config.setLiveWindowName(config.getLiveWindowName() + "Header");
    }
    pidController.setName(config.getLiveWindowName(), config.getPIDName());
    LiveWindow.add(pidController);

  }

  /**
   * Sets the setpoint for pidController
   * 
   * @param setpoint Target point
   */
  public void setSetpoint(double setpoint) {
    pidController.setSetpoint(setpoint);
  }

  /**
   * Sets current setpoint to currentsetpoint + delta
   * 
   * @param delta
   */
  public void setRelativeSetpoint(double delta) {
    pidController.setSetpoint(pidController.getSetpoint() + delta);
  }

  /**
   * Enables the pidController
   */
  public void enable() {
    pidController.enable();
  }

  public boolean isEnabled() {
    return pidController.isEnabled();
  }

  /**
   * Resets the pidController
   */
  public void reset() {
    pidController.reset();
  }

  /**
   * Stops the pidController
   */
  public void disable() {
    pidController.disable();
  }

  /**
   * @return true if the pidController is on target
   */
  public boolean onTarget() {
    return pidController.onTarget();
  }

  private PIDConfiguration gitConfig() {
    return config;
  }

}
