package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.typesafe.config.Config;

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
  private IntakeArmPositionsEnum currentIntakePosition = IntakeArmPositionsEnum.UNKNOWN;

  public RealIntake() {
    Config conf = Robot.getConfig();

    // TODO: only put ports in port config and put intake config in subsystem block
    Config intakeConf = conf.getConfig("port.can");
    rollerTalon = new WPI_TalonSRX(intakeConf.getInt("rollerTalon"));
    intakeArmTalon = new WPI_TalonSRX(intakeConf.getInt("intakeArm"));
    intakeDownDirection = intakeConf.getInt("intakeDownDirection");
  }

  @Override
  public void rollIntake(double speed) {
    rollerTalon.set(speed);

  }

  @Override
  public void moveIntakeArm(double speed) {
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