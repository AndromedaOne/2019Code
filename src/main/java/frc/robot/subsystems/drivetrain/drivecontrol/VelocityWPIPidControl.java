package frc.robot.subsystems.drivetrain.drivecontrol;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;
import frc.robot.TalonSRX_4905;
import frc.robot.closedloopcontrollers.pidcontrollers.PIDConfiguration;
import frc.robot.closedloopcontrollers.pidcontrollers.PIDMultiton;
import frc.robot.subsystems.drivetrain.DriveTrain.RobotGear;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class VelocityWPIPidControl extends DriveControl {

    DifferentialDrive m_differentialDrive;
    VelocityWPIPidTalon velocityWPIPidLeftMaster;
    VelocityWPIPidTalon velocityWPIPidRightMaster;

    public VelocityWPIPidControl(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster) {
        super(leftMaster, rightMaster);
        velocityWPIPidLeftMaster = (VelocityWPIPidTalon) leftMaster;
        velocityWPIPidRightMaster = (VelocityWPIPidTalon) rightMaster;

        String gear;
        switch(Robot.driveTrain.getGear()){
            case HIGHGEAR:
                gear = "HighGear";

            case SLOWHIGHGEAR:
                gear = "HighGear";

            case LOWGEAR:
                gear = "LowGear";

            case SLOWLOWGEAR:
                gear = "LowGear";

            default:
                gear = "LowGear";
        }

        setPID(velocityWPIPidLeftMaster, "left", gear);
        setPID(velocityWPIPidRightMaster, "right", gear);
        m_differentialDrive = new DifferentialDrive(velocityWPIPidLeftMaster, velocityWPIPidRightMaster);
    }

    @Override
    public void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput) {
        m_differentialDrive.arcadeDrive(forwardBackwardSpeed, rotateAmount, squaredInput);

    }

    @Override
    public void stop() {
        m_differentialDrive.stopMotor();
        velocityWPIPidLeftMaster.stopMotor();
        velocityWPIPidRightMaster.stopMotor();
    }

    private void setPID(VelocityWPIPidTalon _talon, String side, String gear) {

        double p = 0.0;
        double i = 0.0;
        double d = 0.0;
        double f = 0.0;

        Config conf = Robot.getConfig();
        Config driveConf = conf.getConfig("ports.driveTrain");

        p = readPIDConfigItem(driveConf, side, gear + "P", 0);
        i = readPIDConfigItem(driveConf, side, gear + "I", 0);
        d = readPIDConfigItem(driveConf, side, gear + "D", 0);
        f = readPIDConfigItem(driveConf, side, gear + "F", 0);

        _talon.setP(p);
        _talon.setI(i);
        _talon.setD(d);
        _talon.setF(f);
    }

    private double readPIDConfigItem(Config driveConf, String side, String configItem, double defaultValue) {
        double configValue = defaultValue;
        if (driveConf.hasPath(side + "Side" + configItem)) {
            configValue = driveConf.getDouble(side + "Side" + configItem);
        }
        System.out.println(side + "Side" + configItem + "=" + configValue);
        return configValue;
    }

    private class VelocityWPIPidTalon extends TalonSRX_4905 {

        PIDMultiton m_pidMultiton;
        
        public VelocityWPIPidTalon(int port) {
            super(port); 
            PIDConfiguration pidConfiguration = new PIDConfiguration();
            
            VelocityWPIPidTalonIn velocityWPIPidTalonIn = new VelocityWPIPidTalonIn();

            VelocityWPIPidControlOut velocityWPIPidControlOut = new VelocityWPIPidControlOut();
            pidConfiguration.setLiveWindowName("DriveTrain");
            pidConfiguration.setPIDName("WPITalonVelocityPID");

            m_pidMultiton = PIDMultiton.getInstance(velocityWPIPidTalonIn, velocityWPIPidControlOut, pidConfiguration);

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

        private class VelocityWPIPidTalonIn implements PIDSource {

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

        }


        private class VelocityWPIPidControlOut implements PIDOutput {

            @Override
            public void pidWrite(double output) {
                set(ControlMode.PercentOutput, output);
            }


        }
    }

    @Override
    public void shiftSpeed(RobotGear robotGear) {
        
        switch(robotGear){
            case HIGHGEAR:
                setPID(velocityWPIPidLeftMaster, "left", "HighGear");
                setPID(velocityWPIPidRightMaster, "right", "HighGear");
                break;

            case SLOWHIGHGEAR:
                setPID(velocityWPIPidLeftMaster, "left", "HighGear");
                setPID(velocityWPIPidRightMaster, "right", "HighGear");
                break;

            case LOWGEAR:
                setPID(velocityWPIPidLeftMaster, "left", "LowGear");
                setPID(velocityWPIPidRightMaster, "right", "LowGear");
                break;
            
            case SLOWLOWGEAR:
                setPID(velocityWPIPidLeftMaster, "left", "LowGear");
                setPID(velocityWPIPidRightMaster, "right", "LowGear");
                break;
        }
        

    }

}