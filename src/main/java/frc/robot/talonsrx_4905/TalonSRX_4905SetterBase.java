package frc.robot.talonsrx_4905;

public abstract class TalonSRX_4905SetterBase {

    protected TalonSRX_4905 m_talonSRX_4905;

    public TalonSRX_4905SetterBase (TalonSRX_4905 talonSRX_4905) {
        m_talonSRX_4905 = talonSRX_4905;
    }
    public abstract void set(double speed);
}