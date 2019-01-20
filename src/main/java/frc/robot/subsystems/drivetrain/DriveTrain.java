package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import frc.robot.Robot;
import frc.robot.commands.TeleOpDrive;
/**
 *
 */
public abstract class DriveTrain extends Subsystem {
    
    @Override
    public void periodic() {
    }

    public abstract void move(double forwardBackSpeed, double rotateAmount);

    public abstract void stop();
    
}