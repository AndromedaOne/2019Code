package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonSRX_4905 extends WPI_TalonSRX {
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