package frc.robot.groupcommands;

import edu.wpi.first.wpilibj.command.Command;

public class DoubleMoveDrivetrainWithEncoderScheduler extends Command {

    private double distanceInInches = 0;

    public DoubleMoveDrivetrainWithEncoderScheduler(double distanceInInches) {
        this.distanceInInches = distanceInInches;
    }
    @Override
    public void initialize(){
        (new DoubleMoveDrivetrainWithEncoder(this.distanceInInches)).start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}