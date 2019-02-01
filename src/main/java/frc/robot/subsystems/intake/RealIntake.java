package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Robot;

/* TODO 
* Test to make sure speed is accurate in RAISEARMSPEED
* Add intakeDownDirection to config file
* find actual values for CARGOPOSITION and ENDGAMEPOSITION
*/
public class RealIntake extends Intake {
    public static WPI_TalonSRX rollerTalon;
    public static WPI_TalonSRX intakeArmTalon;
    private int intakeDownDirection;
   

    private DigitalInput startPositionLimitSwitch;

    private double RAISEARMSPEED = 0.5;
    private int CARGOPOSITION = 100;
    private int ENDGAMEPOSITION = 200;

    public RealIntake() {
        Config conf = Robot.getConfig();
        Config intakeConf = conf.getConfig("port.can");
        rollerTalon = new WPI_TalonSRX(intakeConf.getInt("rollerTalon"));
        intakeArmTalon = new WPI_TalonSRX(intakeConf.getInt("intakeArm"));
        intakeDownDirection = intakeConf.getInt("intakeDownDirection");
        startPositionLimitSwitch = new DigitalInput(0);
    }

    @Override
    public void rollIntake(double speed) {
        rollerTalon.set(speed);

    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void moveToStartPosition() {
        if (isArmLimitSwitchPressed()) {
            intakeArmTalon.set(0);
        } else {
            intakeArmTalon.set(-RAISEARMSPEED * intakeDownDirection);
        }
    }

    @Override
    public void moveToCargoPosition() {
        moveArmToPosition(CARGOPOSITION);
    }

    @Override
    public void moveToEndgamePosition() {
        moveArmToPosition(ENDGAMEPOSITION);
    }

    private void moveArmToPosition(int targetPosition) {
        // TODO use a PID controller here :(
    } 

    /**
     * returns true if the limit switch is pressed 
     * note that startPositionLimitSwitch.get returns false 
     * We made your lives better (you're welcome)
     * @return
     */
    private boolean isArmLimitSwitchPressed() {
        if (startPositionLimitSwitch.get()) {
            return true;
        } else {
            return false;
        }
    }

}