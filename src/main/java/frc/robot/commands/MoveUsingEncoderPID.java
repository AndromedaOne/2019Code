package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.DrivetrainEncoderPIDController;


public class MoveUsingEncoderPID extends Command{

    private DrivetrainEncoderPIDController drivetrainEncoder = 
    DrivetrainEncoderPIDController.getInstance();

    private int _setpoint = 0;

    public MoveUsingEncoderPID(int setpoint){
        drivetrainEncoder.setPID(0.001, 0, 0);
        drivetrainEncoder.setAbsoluteTolerance(200);
        drivetrainEncoder.setOutputRange(1);
        _setpoint = setpoint;
    }
    public void initialize(){
        System.out.println(" -- Called! -- ");
        drivetrainEncoder.initialize();
        drivetrainEncoder.enable(_setpoint);
    }

    public void execute(){
    }

    public boolean isFinished(){
        return drivetrainEncoder.isDone();
    }

    public void end(){
        drivetrainEncoder.stop();
    }

    public void interrupt(){
        drivetrainEncoder.stop();
    }
}

