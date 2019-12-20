package frc.robot.subsystems.drivetrain.drivecontrol;

import frc.robot.TalonSRX_4905;

public class VelocityWPIPidControl extends DriveControl {

    public VelocityWPIPidControl(TalonSRX_4905 leftMaster, TalonSRX_4905 rightMaster){
        super(leftMaster, rightMaster);
    }

    @Override
    public void move(double forwardBackwardSpeed, double rotateAmount, boolean squaredInput) {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    } 

}