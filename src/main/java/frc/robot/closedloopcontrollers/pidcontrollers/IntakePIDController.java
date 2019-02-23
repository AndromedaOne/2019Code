package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveIntakeSafely;
import frc.robot.sensors.anglesensor.AngleSensor;
import frc.robot.sensors.limitswitchsensor.LimitSwitchSensor.IsAtLimitException;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class IntakePIDController extends PIDControllerBase {

  private static IntakePIDController instance;
  private IntakePIDOut intakePIDOut;
  private AngleSensor intakeAngleSensor;

  private IntakePIDController() {
    super.absoluteTolerance = 3;
    super.p = 0;
    super.i = 0;
    super.d = 0;
    super.outputRange = 0.3;
    super.subsytemName = "Intake";
    super.pidName = "IntakePID";

    intakeAngleSensor = Robot.intakeAngleSensor;
    super.trace = Trace.getInstance();
    intakeAngleSensor.putSensorOnLiveWindow(super.subsytemName, "Intake");
    intakePIDOut = new IntakePIDOut();
    super.setPIDConfiguration(super.pidConfiguration);
    super.pidMultiton = PIDMultiton.getInstance(intakeAngleSensor, intakePIDOut, super.pidConfiguration);
    intakePIDOut.setContainer(super.pidMultiton);
  }

  private class IntakePIDOut implements PIDOutput {
    PIDMultiton container;

    public void setContainer(PIDMultiton containerParam) {
      container = containerParam;
    }

    @Override
    public void pidWrite(double output) {
      trace.addTrace(true, "IntakePID", new TracePair("Output", output),
          new TracePair("Setpoint", container.getSetpoint()), new TracePair("Angle", intakeAngleSensor.pidGet()));
      try {
        MoveIntakeSafely.moveIntake(output);
      } catch (IsAtLimitException e) {
        System.out.println("The Intake PID loop is driving into the limit switch!");
        container.disable();
      }
    }
  }

  public static IntakePIDController getInstance() {
    System.out.println(" --- Asking for Instance ---  IntakePID");
    if (instance == null) {
      System.out.println("Creating new Intake PID Controller");
      instance = new IntakePIDController();
    }
    return instance;
  }

}