package frc.robot.closedloopcontrollers.pidcontrollers;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.TalonSRX_4905;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.PIDConfiguration;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.PIDMultiton;
import frc.robot.sensors.SensorBase;

public class VelocityWPIPidTalon extends TalonSRX_4905 {

    private PIDMultiton m_pidMultiton;
    
    public VelocityWPIPidTalon(int port, double p, double i, double d, double f, String liveWindowName, String pidName) {
        super(port); 
        PIDConfiguration pidConfiguration = new PIDConfiguration(p, i, d, f, 0, 1.0, 0, liveWindowName, pidName);
        
        VelocityWPIPidTalonIn velocityWPIPidTalonIn = new VelocityWPIPidTalonIn();

        VelocityWPIPidControlOut velocityWPIPidControlOut = new VelocityWPIPidControlOut();
        pidConfiguration.setLiveWindowName("DriveTrain");
        pidConfiguration.setPIDName("WPITalonVelocityPID");

        m_pidMultiton = PIDMultiton.getInstance(velocityWPIPidTalonIn, velocityWPIPidControlOut, pidConfiguration);
        velocityWPIPidTalonIn.putSensorOnLiveWindow("DriveTrain", "WPITalonVelocityPIDIn");
    }
    
    @Override
    public void set(double speed) {
        super.speed = speed;
        m_pidMultiton.setSetpoint(speed);
        m_pidMultiton.enable();
        feed();
    }

    public void setP(double p) {
        m_pidMultiton.pidController.setF(p);
    }

    public void setI(double i) {
        m_pidMultiton.pidController.setI(i);
    }

    public void setD(double d) {
        m_pidMultiton.pidController.setD(d);
    }

    public void setF(double f) {
        m_pidMultiton.pidController.setF(f);
    }

    private class VelocityWPIPidTalonIn extends SensorBase implements PIDSource {

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
            return getSelectedSensorVelocity();
        }

        @Override
        public void putSensorOnLiveWindow(String subsystemNameParam, String sensorNameParam) {
            putReadingOnLiveWindow(subsystemNameParam, sensorNameParam + "PidGet", this::pidGet);

        }

    }


    private class VelocityWPIPidControlOut implements PIDOutput {

        @Override
        public void pidWrite(double output) {
            set(ControlMode.PercentOutput, output);
        }


    }
}