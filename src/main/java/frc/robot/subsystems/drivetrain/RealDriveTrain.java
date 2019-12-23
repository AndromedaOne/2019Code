package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.TeleOpDrive;
import frc.robot.subsystems.drivetrain.drivecontrol.DriveControl;
import frc.robot.subsystems.drivetrain.drivecontrol.DriveControlFactory;
import frc.robot.talonsrx_4905.TalonSRX_4905;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;


/* For the Java CTRE Talon API Docs, see this link:
http://www.ctr-electronics.com/downloads/api/java/html/index.html
*/

/**
 *
 */
public class RealDriveTrain extends DriveTrain {
  
  public static DoubleSolenoid shifterSolenoid;
  private boolean shifterPresentFlag = false;
  private boolean invertTurning = false;

  private DriveControl m_driveControl;
  // private PowerDistributionPanel pdp = new PowerDistributionPanel();

  public boolean getShifterPresentFlag() {
    return shifterPresentFlag;
  }

  public RealDriveTrain() {
    Config conf = Robot.getConfig();
    Config driveConf = conf.getConfig("ports.driveTrain");
    
    if (conf.hasPath("subsystems.driveTrain.invertTurning")) {
      invertTurning = conf.getBoolean("subsystems.driveTrain.invertTurning");
    }

    // Gear Shift Solenoid
    if (Robot.getConfig().hasPath("subsystems.driveTrain.shifter")) {
      shifterPresentFlag = true;
      shifterSolenoid = new DoubleSolenoid(driveConf.getInt("pneumatics.forwardChannel"),
          driveConf.getInt("pneumatics.backwardsChannel"));
    }
    m_driveControl = DriveControlFactory.create();
  }

  private RobotGear currentGear = RobotGear.SLOWHIGHGEAR;

  private double lowGearMaxSpeed = 1;
  private double highGearMaxSpeed = 1;
  private final double kFirstGearModifier = 0.6;
  private final double kThirdGearModifier = 0.75;
  private final double kAccelerationSlope = 1.0 / 12.5;
  private double previousSpeed = 0;
  private double gearMod = 1;
  private int shifterDelayCounter = 0;
  private int delay = 4;
  private double maxSpeed = lowGearMaxSpeed;
  public static final int kLowGearPIDSlot = 0;
  public static final int kHighGearPIDSlot = 1;
  private int slotIdx = 0;

  @Override
  public void setGear(RobotGear gear) {
    currentGear = gear;
    switch (currentGear) {
    case SLOWLOWGEAR:
      gearMod = kFirstGearModifier;
      Robot.leftLeds.setRed(1.0);
      Robot.rightLeds.setRed(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 1);
      shiftToLowGear();
      break;
    case LOWGEAR:
      gearMod = 1;
      Robot.leftLeds.setWhite(1.0);
      Robot.rightLeds.setWhite(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 2);
      shiftToLowGear();
      break;
    case SLOWHIGHGEAR:
      gearMod = kThirdGearModifier;
      Robot.leftLeds.setBlue(1.0);
      Robot.rightLeds.setBlue(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 3);
      shiftToHighGear();
      break;
    case HIGHGEAR:
      gearMod = 1;
      Robot.leftLeds.setGreen(1.0);
      Robot.rightLeds.setGreen(1.0);
      SmartDashboard.putNumber("CurrentSpeed", 4);
      shiftToHighGear();
      break;
    }
  }

  @Override
  public RobotGear getGear() {
    return currentGear;
  }

  public void toggleShifter() {
    switch (currentGear) {
    case SLOWLOWGEAR:
      System.out.println(" - Switching to Slow Low Gear - ");
      setGear(RobotGear.SLOWHIGHGEAR);
      break;
    case LOWGEAR:
      System.out.println(" - Switching to Low Gear - ");
      // This is due to Erics preference to shift from Low Gear to Slow High
      setGear(RobotGear.SLOWHIGHGEAR);
      break;
    case SLOWHIGHGEAR:
      System.out.println(" - Switching to High Gear - ");
      setGear(RobotGear.SLOWLOWGEAR);
      break;
    case HIGHGEAR:
      System.out.println(" - Switching to Slow High Gear - ");
      setGear(RobotGear.LOWGEAR);
      break;
    }
  }

  public void toggleSlowMode() {
    switch (currentGear) {
    case SLOWLOWGEAR:
      System.out.println(" - Switching to Slow Low Gear - ");
      setGear(RobotGear.LOWGEAR);
      break;
    case LOWGEAR:
      System.out.println(" - Switching to Low Gear - ");
      setGear(RobotGear.SLOWLOWGEAR);
      break;
    case SLOWHIGHGEAR:
      System.out.println(" - Switching to High Gear - ");
      setGear(RobotGear.HIGHGEAR);
      break;
    case HIGHGEAR:
      System.out.println(" - Switching to Slow High Gear - ");
      setGear(RobotGear.SLOWHIGHGEAR);
      break;
    }
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleOpDrive());
  }

  @Override
  public void periodic() {
  }

  public void move(double forwardBackSpeed, double rotateAmount, boolean squaredInputs, boolean useAccelLimits) {
    // logMeasurements("Left", driveTrainLeftMaster, forwardBackSpeed, false);
    // logMeasurements("Right", driveTrainRightMaster, -forwardBackSpeed, true);
    if (invertTurning) {
      rotateAmount = -rotateAmount;
    }
    shifterDelayCounter++;
    double requestedSpeed = forwardBackSpeed * gearMod;
    if (useAccelLimits) {
      if (requestedSpeed > previousSpeed) {
        previousSpeed += kAccelerationSlope;
        if (previousSpeed > requestedSpeed) {
          previousSpeed = requestedSpeed;
        }
      } else {
        previousSpeed -= kAccelerationSlope;
        if (previousSpeed < requestedSpeed) {
          previousSpeed = requestedSpeed;
        }
      }
    } else {
      previousSpeed = requestedSpeed;
    }

    double currentSpeed = previousSpeed;
    double currentRotate = rotateAmount;
    if (shifterDelayCounter >= delay) {
      Robot.driveTrain.changeNeutralMode(NeutralMode.Brake);
    } else {
      currentSpeed = 0;
      currentRotate = 0;
    }
    m_driveControl.move(currentSpeed, currentRotate * gearMod, squaredInputs);
    Trace.getInstance().addTrace(false, "move", new TracePair<>("ForwardBack", previousSpeed),
        new TracePair<>("Rotate", rotateAmount));
  }

  /* String for output */
  StringBuilder _sb = new StringBuilder();
  int _loops = 0;

  private void logMeasurements(String side, WPI_TalonSRX _talon, double targetVelocity, boolean doneMeasuring) {
    /* Get Talon/Victor's current output percentage */
    if (_talon == null) {
      System.out.println("TALON IS NULL!!! ");
    }
    double motorOutput = _talon.getMotorOutputPercent();
    Trace.getInstance().addTrace(false, "VCMeasure" + side, new TracePair<>("Percent", (double) motorOutput * 100),
        new TracePair<>("Speed", (double) _talon.getSelectedSensorVelocity(slotIdx)),
        new TracePair<>("Error", (double) _talon.getClosedLoopError(slotIdx)),
        new TracePair<>("Target", (double) _talon.getClosedLoopTarget(slotIdx)));
  }

  public void stop() {
    m_driveControl.stop();
  }

  public WPI_TalonSRX getLeftRearTalon() {
    return m_driveControl.getLeftMaster();
  }

  public WPI_TalonSRX getRightRearTalon() {
    return m_driveControl.getRightMaster();
  }

  public void shiftToLowGear() {
    System.out.println("Shifting to low gear");
    if (shifterSolenoid != null) {
      changeNeutralMode(NeutralMode.Coast);
      shifterSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    maxSpeed = lowGearMaxSpeed;
    m_driveControl.shiftSpeed(RobotGear.LOWGEAR);
    slotIdx = 0;
    shifterDelayCounter = 0;
  }

  public void shiftToHighGear() {
    System.out.println("Shifting to high gear");
    if (shifterSolenoid != null) {
      changeNeutralMode(NeutralMode.Coast);
      shifterSolenoid.set(DoubleSolenoid.Value.kReverse);
      maxSpeed = highGearMaxSpeed;
      m_driveControl.shiftSpeed(RobotGear.HIGHGEAR);
      slotIdx = 1;
      shifterDelayCounter = 0;
    } else {
      System.out.println("NO SHIFTER");
    }
  }

  @Override
  public void changeNeutralMode(NeutralMode mode) {
    for(TalonSRX_4905 a : DriveControlFactory.getSpeedControllers()) {
      a.setNeutralMode(mode);
    }
  }
}
