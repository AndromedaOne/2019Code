package frc.robot.subsystems.intake;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.Robot;
import frc.robot.commands.TeleopIntake;

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
  private double cargoPositionSetPoint;
  private double groundPositionSetPoint;

  public RealIntake() {
    Config conf = Robot.getConfig();

    // TODO: only put ports in port config and put intake config in subsystem block
    Config intakeConf = conf.getConfig("ports.intake");
    rollerTalon = new PWMVictorSPX(intakeConf.getInt("rollerTalon"));
    intakeArmTalon = new PWMVictorSPX(intakeConf.getInt("intakeArm"));
    intakeDownDirection = intakeConf.getInt("intakeDowndirection");
    if (conf.hasPath("subsystems.intake")) {
      Config intakeSubConf = conf.getConfig("subsystems.intake");
      cargoPositionSetPoint = intakeSubConf.getDouble("CargoPositionSetpoint");
      groundPositionSetPoint = intakeSubConf.getDouble("GroundPositionSetPoint");
    } else {
      cargoPositionSetPoint = 7;
      groundPositionSetPoint = 6;
    }
  }

  @Override
  public void rollIntake(double speed) {
    rollerTalon.set(speed);

  }

  @Override
  public void moveIntakeArm(double speed) {
    double sign = 1;
    if (speed < 0) {
      sign = -1;
    }
    intakeArmTalon.set(Math.pow(speed * intakeDownDirection, 2) * sign);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new TeleopIntake());
  }

  @Override
  public IntakeArmPositionsEnum getCurrentIntakeArmPosition() {
    return currentIntakePosition;

  }

  @Override
  public void setCurrentIntakeArmPosition(IntakeArmPositionsEnum position) {
    currentIntakePosition = position;
  }

  public boolean isAtLimitSwitch() {
    return !Robot.intakeStowedSwitch.isAtLimit();
  }

  public double getCargoSetpoint() {
    return cargoPositionSetPoint;
  }

  public double getGroundSetpoint() {
    return groundPositionSetPoint;
  }

  @Override
  public boolean isAtGround() {
    if (Robot.intakeAngleSensor.getAngle() > groundPositionSetPoint) {
      return true;
    } else {
      return false;
    }
  }

}