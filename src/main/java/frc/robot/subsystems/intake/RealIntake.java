package frc.robot.subsystems.intake;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.Robot;

/* TODO 
* Test to make sure speed is accurate in RAISEARMSPEED
* Add intakeDownDirection to config file
* find actual values for CARGOPOSITION and ENDGAMEPOSITION
*/
public class RealIntake extends Intake {
  public static PWMVictorSPX rollerTalon;
  public static PWMVictorSPX intakeArmTalon;
  private int intakeDownDirection;
  private IntakeArmPositionsEnum currentIntakePosition = IntakeArmPositionsEnum.UNKNOWN;

  public RealIntake() {
    Config conf = Robot.getConfig();

    // TODO: only put ports in port config and put intake config in subsystem block
    Config intakeConf = conf.getConfig("ports.intake");
    rollerTalon = new PWMVictorSPX(intakeConf.getInt("rollerTalon"));
    intakeArmTalon = new PWMVictorSPX(intakeConf.getInt("intakeArm"));
    intakeDownDirection = intakeConf.getInt("intakeDowndirection");
  }

  @Override
  public void rollIntake(double speed) {
    rollerTalon.set(speed);

  }

  @Override
  public void moveIntakeArm(double speed) {
    System.out.println(speed * intakeDownDirection);
    intakeArmTalon.set(speed * intakeDownDirection);
  }

  @Override
  protected void initDefaultCommand() {

  }

  @Override
  public IntakeArmPositionsEnum getCurrentIntakeArmPosition() {
    return currentIntakePosition;

  }

  @Override
  public void setCurrentIntakeArmPosition(IntakeArmPositionsEnum position) {
    currentIntakePosition = position;
  }

}