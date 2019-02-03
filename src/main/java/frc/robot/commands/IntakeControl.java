package frc.robot.commands;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.pidcontrollers.IntakePIDController;


public class IntakeControl extends Command {

    public enum MoveIntakeArmDirection {UP, DOWN};

    private IntakePIDController intakePositionsPID = IntakePIDController.getInstance();

    static IntakePositionsEnum currentIntakePosition = IntakePositionsEnum.STOWED;
    private MoveIntakeArmDirection directionToMove;
    private IntakePositionsEnum nextIntakePosition;

    private static double stowedPositionSetPoint;
    private static double cargoPositionSetPoint;
    private static double groundPositionSetPoint;
    static {
        Config conf = Robot.getConfig();
        Config intakeConf = conf.getConfig("port.can");
        stowedPositionSetPoint = intakeConf.getDouble("StowedPositionSetpoint");
        cargoPositionSetPoint = intakeConf.getDouble("CargoPositionSetpoint");
        groundPositionSetPoint = intakeConf.getDouble("GroundPositionSetPoint");
    }
/**
 * Construct an intake control command to make the intake arm go up or down
 * 
 * @param directionToMove - MoveIntakeArmDirection.UP or MoveIntakeArmDirection.DOWN
 */
    public IntakeControl(MoveIntakeArmDirection directionToMove){
        this.directionToMove = directionToMove;
        requires(Robot.intake);
    }
   
    @Override
    protected void initialize(){
        switch (directionToMove) {
        case UP:
            setUpSetPoint();
            break;
            
        case DOWN:
            setDownSetpoint();
            break;
        }
        intakePositionsPID.enable();
    }
/**
 * Moves intake up as far as it can go and does not try to go further when at Stowed
 */
    private void setUpSetPoint() {
        switch (currentIntakePosition) {
        case STOWED:
            intakePositionsPID.setSetpoint(stowedPositionSetPoint);
            nextIntakePosition = IntakePositionsEnum.STOWED;
            break;
        case CARGOHEIGHT:
            intakePositionsPID.setSetpoint(stowedPositionSetPoint);
            nextIntakePosition = IntakePositionsEnum.STOWED;
            break;
        case GROUNDHEIGHT:
            intakePositionsPID.setSetpoint(cargoPositionSetPoint);
            nextIntakePosition = IntakePositionsEnum.CARGOHEIGHT;
            break;
        }   
    }

/**
 * Moves intake down as far as it can go and does not try to go further when at Groundheight
 */
    private void setDownSetpoint() {
        switch (currentIntakePosition) {
        case STOWED:
            intakePositionsPID.setSetpoint(cargoPositionSetPoint);
            nextIntakePosition = IntakePositionsEnum.CARGOHEIGHT;            
            break;
        case CARGOHEIGHT:
            intakePositionsPID.setSetpoint(groundPositionSetPoint);
            nextIntakePosition = IntakePositionsEnum.GROUNDHEIGHT;
            break;
        case GROUNDHEIGHT:
            intakePositionsPID.setSetpoint(groundPositionSetPoint);
            nextIntakePosition = IntakePositionsEnum.GROUNDHEIGHT;
            break;
        }
    }

    @Override
    protected void execute(){
    }
   
    @Override
    protected boolean isFinished() {
         return intakePositionsPID.onTarget();
    }

    @Override
    protected void end(){
        intakePositionsPID.disable();
        if (intakePositionsPID.onTarget()) {
            currentIntakePosition = nextIntakePosition;
        }
    }

    @Override
    protected void interrupted(){
        end();
    }
}