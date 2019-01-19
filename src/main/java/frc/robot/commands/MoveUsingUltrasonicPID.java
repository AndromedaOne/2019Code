package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.closedloopcontrollers.ClosedLoopUltrasonicPIDController;
import frc.robot.sensors.UltrasonicSensor;


public class MoveUsingUltrasonicPID extends Command{

    private UltrasonicPIDController ultrasonic = 
    UltrasonicPIDController.getInstance();

    public MoveUsingUltrasonicPID(){
    }

    public void initialize() {
        System.out.println(" --ClalledUS-- ");
        //UltrasonicPID
        ultrasonic.setPID(1,0,0);
        ultrasonic.setAbsoluteTolerance(5);
        ultrasonic.setOutputRange(3);
        ultrasonic.initialize();
        ultrasonic.enable(12);
    }
    public void excute(){

    }
    public boolean isFinished(){
        return false;
    }
    public void end(){
    }
    public void interupt(){
    }
}

