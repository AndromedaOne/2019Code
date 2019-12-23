package frc.robot.closedloopcontrollers.pidcontrollers.velocitypidwpi;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.PIDConfiguration;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.PIDMultiton;
import frc.robot.sensors.SensorBase;
import frc.robot.talonsrx_4905.TalonSRX_4905;
import frc.robot.talonsrx_4905.TalonSRX_4905SetterBase;

public class VelocityPidWPIForTalon extends TalonSRX_4905SetterBase{

    private PIDMultiton m_pidMultiton;
    
    protected VelocityPidWPIForTalon(TalonSRX_4905 m_talonSRX_4905, double p, double i, double d, double f, String liveWindowName, String pidName) {
        super(m_talonSRX_4905);
        PIDConfiguration pidConfiguration = new PIDConfiguration(p, i, d, f, 0, 1.0, 0, liveWindowName, pidName);
        
        VelocityWPIPidTalonIn velocityWPIPidTalonIn = new VelocityWPIPidTalonIn();

        VelocityWPIPidControlOut velocityWPIPidControlOut = new VelocityWPIPidControlOut();
        pidConfiguration.setLiveWindowName("DriveTrain");
        pidConfiguration.setPIDName("WPITalonVelocityPID");

        m_pidMultiton = PIDMultiton.getInstance(velocityWPIPidTalonIn, velocityWPIPidControlOut, pidConfiguration);
        velocityWPIPidTalonIn.putSensorOnLiveWindow("DriveTrain", "WPITalonVelocityPIDIn");
    }

    protected void setPIDF(double p, double i, double d, double f) {
        m_pidMultiton.setPIDTerms(p, i, d);
        m_pidMultiton.pidController.setF(f);
    }

    private class VelocityWPIPidTalonIn extends SensorBase implements PIDSource {
        public VelocityWPIPidTalonIn() {

        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
            // TODO Auto-generated method stub

        }

        @Override
        public PIDSourceType getPIDSourceType() {
            // TODO Auto-generated method stub
            return PIDSourceType.kRate;
        }

        @Override
        public double pidGet() {
            // TODO Auto-generated method stub
            return m_talonSRX_4905.getSelectedSensorVelocity();
        }

        @Override
        public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
            putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "PidGet", this::pidGet);

        }

    }


    private class VelocityWPIPidControlOut implements PIDOutput {

        @Override
        public void pidWrite(double output) {
            m_talonSRX_4905.set(ControlMode.PercentOutput, output);
        }


    }

    @Override
    public void set(double speed) {
        m_pidMultiton.setSetpoint(speed);
        m_pidMultiton.enable();
    }

    public void shiftToHighGear() {

    }
    public void shiftToLowGear() {
        
    }

}