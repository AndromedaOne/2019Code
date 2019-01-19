package frc.robot.subsystems;

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
public class DriveTrain extends Subsystem {
    private static WPI_TalonSRX driveTrainLeftFrontTalon;
    private static WPI_TalonSRX driveTrainLeftRearTalon;
    private static SpeedControllerGroup driveTrainLeftSpeedController;
    private static WPI_TalonSRX driveTrainRightFrontTalon;
    private static WPI_TalonSRX driveTrainRightRearTalon;
    private static SpeedControllerGroup driveTrainRightSpeedController;
    private static DifferentialDrive differentialDrive;

    public DriveTrain(){
        Config conf = Robot.getConfig();
        Config driveConf = conf.getConfig("ports.driveTrain");
        driveTrainLeftFrontTalon = new WPI_TalonSRX(driveConf.getInt("leftFrontTalon"));
        driveTrainLeftRearTalon = new WPI_TalonSRX(driveConf.getInt("leftRearTalon"));
        driveTrainLeftSpeedController = new SpeedControllerGroup(driveTrainLeftFrontTalon, driveTrainLeftRearTalon);
        driveTrainRightFrontTalon = new WPI_TalonSRX(driveConf.getInt("rightFrontTalon"));
        System.out.println(" -- I'm creating the Right Rear Talon -- ");
        driveTrainRightRearTalon = new WPI_TalonSRX(driveConf.getInt("rightRearTalon"));
        driveTrainRightSpeedController = new SpeedControllerGroup(driveTrainRightFrontTalon, driveTrainRightRearTalon);
        differentialDrive = new DifferentialDrive(driveTrainLeftSpeedController, driveTrainRightSpeedController);
    }

    public static WPI_TalonSRX getRightRearEncoder(){
        return driveTrainRightRearTalon;
    }
    
    @Override
    public void initDefaultCommand(){
        setDefaultCommand(new TeleOpDrive());
    }

    @Override
    public void periodic() {
    }

    public void move(double forwardBackSpeed, double rotateAmount) {
    	differentialDrive.arcadeDrive(forwardBackSpeed, rotateAmount);
    }
    public void stop() {
    	differentialDrive.stopMotor();
    }
    
}