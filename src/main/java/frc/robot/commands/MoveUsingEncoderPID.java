package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.DrivetrainEncoderPIDController;


public class MoveUsingEncoderPID extends Command{

    private DrivetrainEncoderPIDController drivetrainEncoder = 
    DrivetrainEncoderPIDController.getInstance();

    public MoveUsingEncoderPID(){

    }
    public void initialize(){
        System.out.println(" -- Called! -- ");
        // Encoder PID
        drivetrainEncoder.setPID(0.1, 0, 0);
        drivetrainEncoder.setAbsoluteTolerance(250);
        drivetrainEncoder.setOutputRange(1);
        drivetrainEncoder.initialize();
        drivetrainEncoder.enable(600);
    }

    public void execute(){

    }

    public boolean isFinished(){
        return false;
    }

    public void end(){

    }

    public void interrupt(){

    }
}

