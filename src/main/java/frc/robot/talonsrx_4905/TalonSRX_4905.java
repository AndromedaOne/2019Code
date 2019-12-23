package frc.robot.talonsrx_4905;

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
    protected ControlMode controlMode = ControlMode.PercentOutput;
    protected double speed;
    TalonSRX_4905SetterBase m_talonSRX_4905Setter;

    public TalonSRX_4905(int deviceNumber) {
      super(deviceNumber);
      m_talonSRX_4905Setter = new TalonSRX_4905SetterControlMode(this, controlMode);
    }

    /**
     * Set the control mode to be used when driving the motor
     * 
     * @param controlMode Typically ControlMode.PercentOutput or
     * ControlMode.Velocity
     */
    public void setControlMode(ControlMode controlMode) {
      this.controlMode = controlMode;
      m_talonSRX_4905Setter = new TalonSRX_4905SetterControlMode(this, controlMode);

    }

    public void switchOutputSetter(TalonSRX_4905SetterBase talonSRX_4905Setter) {
      m_talonSRX_4905Setter = talonSRX_4905Setter;
    }

    public TalonSRX_4905SetterBase getTalonSRX_4905Setter() {
      return m_talonSRX_4905Setter;
    }

    @Override
    public void set(double speed) {
      m_talonSRX_4905Setter.set(speed);
      this.speed = speed;
      feed();
    }

    @Override
    public double get() {
      return speed;
    }
  }