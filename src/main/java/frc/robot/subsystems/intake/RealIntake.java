package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import frc.robot.Robot;

public class RealIntake extends Intake{
    public static WPI_TalonSRX rollerTalon;
    public static WPI_TalonSRX intakeArmTalon;

    public RealIntake(){
        Config conf = Robot.getConfig();
        Config intakeConf = conf.getConfig("port.can");
        rollerTalon = new WPI_TalonSRX(intakeConf.getInt("rollerTalon"));
        intakeArmTalon = new WPI_TalonSRX(intakeConf.getInt("intakeArm"));
    }

    @Override
    public void rollIntake(double speed) {
        rollerTalon.set(speed);
    }

    @Override
    public void moveIntakeArm(double speed) {
        intakeArmTalon.set(speed);
    }

    @Override
    protected void initDefaultCommand() {

    }

}