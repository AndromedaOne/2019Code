package frc.robot.subsystems.intake;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.Robot;
import frc.robot.commands.TeleopIntake;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

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
  private double stowedPositionSetpoint;

  public RealIntake() {
    Config conf = Robot.getConfig();

    Config intakeConf = conf.getConfig("ports.intake");
    rollerTalon = new PWMVictorSPX(intakeConf.getInt("rollerTalon"));
    intakeArmTalon = new PWMVictorSPX(intakeConf.getInt("intakeArm"));
    intakeDownDirection = intakeConf.getInt("intakeDowndirection");
    Config intakeSubConf = conf.getConfig("subsystems.intake");
    cargoPositionSetPoint = intakeSubConf.getDouble("CargoPositionSetpoint");
    groundPositionSetPoint = intakeSubConf.getDouble("GroundPositionSetpoint");
    stowedPositionSetpoint = intakeSubConf.getDouble("StowedPositionSetpoint");

    if (isAtLimitSwitch()) {
      currentIntakePosition = IntakeArmPositionsEnum.STOWED;
    }

  }

  @Override
  public void rollIntake(double speed) {
    rollerTalon.set(speed);

  }

  @Override
  public void moveIntakeArm(double speed) {
    Trace.getInstance().addTrace(true, "IntakeSpeed", new TracePair<>("Speed", speed),
        new TracePair<>("Angle", Robot.intakeAngleSensor.getAngle()));
    intakeArmTalon.set(speed);
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
    return Robot.intakeStowedSwitch.isAtLimit();
  }

  public double getCargoSetpoint() {
    return cargoPositionSetPoint;
  }

  public double getGroundSetpoint() {
    return groundPositionSetPoint;
  }

  public double getStowedSetpoint() {
    return stowedPositionSetpoint;
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