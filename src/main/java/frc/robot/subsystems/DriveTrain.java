package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends Subsystem {
    public static WPI_TalonSRX driveTrainLeftFrontTalon;
    public static WPI_TalonSRX driveTrainLeftRearTalon;
    public static SpeedControllerGroup driveTrainLeftSpeedController;
    public static WPI_TalonSRX driveTrainRightFrontTalon;
    public static WPI_TalonSRX driveTrainRightRearTalon;
    public static SpeedControllerGroup driveTrainRightSpeedController;
    public static DifferentialDrive driveTrainDifferentialDrive;
    
    
   

    @Override
    public void initDefaultCommand() {
        driveTrainLeftFrontTalon = new WPI_TalonSRX(0);
        driveTrainLeftRearTalon = new WPI_TalonSRX(2);
        driveTrainLeftSpeedController = new SpeedControllerGroup(driveTrainLeftFrontTalon, driveTrainLeftRearTalon  );
        driveTrainRightFrontTalon = new WPI_TalonSRX(3);
        driveTrainRightRearTalon = new WPI_TalonSRX(4);
        driveTrainRightSpeedController = new SpeedControllerGroup(driveTrainRightFrontTalon, driveTrainRightRearTalon);
        driveTrainDifferentialDrive = new DifferentialDrive(driveTrainLeftSpeedController, driveTrainRightSpeedController);
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