package frc.robot.talonsrx_4905;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class TalonSRX_4905SetterControlMode extends TalonSRX_4905SetterBase{

    ControlMode m_controlMode;
    public TalonSRX_4905SetterControlMode(TalonSRX_4905 talonSRX_4905, ControlMode controlMode) {
        super(talonSRX_4905);
        m_controlMode = controlMode;
    }

    @Override
    public void set(double speed) {
        m_talonSRX_4905.set(m_controlMode, speed);
    }

}