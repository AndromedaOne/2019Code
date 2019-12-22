
package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveIntakeSafely;
import frc.robot.closedloopcontrollers.pidcontrollers.basepidcontrollers.*;
import frc.robot.sensors.anglesensor.AngleSensor;

public class IntakePIDController {

  private static IntakePIDController instance;
  private IntakePIDOut intakePIDOut;
  private AngleSensor intakeAngleSensor;
  private PIDMultiton pidMultiton;

  private IntakePIDController() {
    double absoluteTolerance = 0.035;
    double p = 0.25;
    double i = 0;
    double d = 0.25;

    double outputRange = 1;
    String subsystemName = "Intake";
    String pidName = "IntakePID";

    PIDConfiguration pidConfiguration = new PIDConfiguration(p, i, d, 0, 0, 1, 1, subsystemName, pidName);

    intakeAngleSensor = Robot.intakeAngleSensor;

    intakeAngleSensor.putSensorOnLiveWindow(subsystemName, "Intake");
    intakePIDOut = new IntakePIDOut();
    intakePIDOut.setContainer(pidMultiton);

    pidMultiton = PIDMultiton.getInstance(intakeAngleSensor, intakePIDOut, pidConfiguration);
  }

  private class IntakePIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
      output = -output;
      MoveIntakeSafely.moveIntake(output);
    }
  }

  public static IntakePIDController getInstance() {
    System.out.println(" --- Asking for Intake PID Instance ---");
    if (instance == null) {
      System.out.println("Creating new Intake PID Controller");
      instance = new IntakePIDController();
    }
    return instance;
  }

  public PIDMultiton getPIDMultiton() {
    return pidMultiton;
  }

  public void enable() {
    pidMultiton.enable();
  }

  public void disable() {
    pidMultiton.disable();
  }

  public boolean onTarget() {
    return pidMultiton.onTarget();
  }

  public void setSetpoint(double setpoint) {
    pidMultiton.setSetpoint(setpoint);
  }

  public boolean isEnabled() {
    return pidMultiton.isEnabled();
  }

}