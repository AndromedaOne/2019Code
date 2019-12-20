package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonSRX_4905 extends WPI_TalonSRX {

  /**
   * Specialization of WPI_TalonSRX
   * 
   * The WPI_TalonSRX class implements a set() method to set the speed of the
   * TalonSRX. Unfortunately, that method <i>always</i> drives the motor in
   * PercentOutput mode. We need to be able drive the motor in Velocity mode to
   * use the closed loop velocity mode. So we extend WPI_TalonSRX and provide our
   * own set() method which allows us to set the control mode (via the newly added
   * setControlMode() method.)
   */
    private ControlMode controlMode = ControlMode.PercentOutput;
    private double speed;

    public TalonSRX_4905(int deviceNumber) {
      super(deviceNumber);
    }

    /**
     * Set the control mode to be used when driving the motor
     * 
     * @param controlMode Typically ControlMode.PercentOutput or
     * ControlMode.Velocity
     */
    public void setControlMode(ControlMode controlMode) {
      this.controlMode = controlMode;
    }

    @Override
    public void set(double speed) {
      this.speed = speed;
      set(controlMode, speed);
      feed();
    }

    @Override
    public double get() {
      return speed;
    }
  }