package frc.robot.closedloopcontrollers;

import frc.robot.Robot;
import frc.robot.sensors.limitswitchsensor.LimitSwitchSensor;

import frc.robot.subsystems.intake.Intake;

public class MoveIntakeSafely {
    Intake intake;
    LimitSwitchSensor intakeStowedSwitch;
   

    public MoveIntakeSafely() {
        intake = Robot.intake;
        intakeStowedSwitch = Robot.intakeStowedSwitch;
    }
    public void moveIntake(double value) throws IsAtLimitException {
        // If the intake is at the limit and the intake is trying to go down:
        if(intakeStowedSwitch.isAtLimit() && value < 0){
            throw this.new IsAtLimitException();
        }
        intake.moveIntake(value);
    }

    public class IsAtLimitException extends Exception {

        private static final long serialVersionUID = 1L;
    
      }
    
}