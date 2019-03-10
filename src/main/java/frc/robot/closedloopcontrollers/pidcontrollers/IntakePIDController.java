
package frc.robot.closedloopcontrollers.pidcontrollers;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Robot;
import frc.robot.closedloopcontrollers.MoveIntakeSafely;
import frc.robot.sensors.anglesensor.AngleSensor;

public class IntakePIDController extends PIDControllerBase {

  private static IntakePIDController instance;
  private IntakePIDOut intakePIDOut;
  private AngleSensor intakeAngleSensor;

  private IntakePIDController() {
    super.absoluteTolerance = 0.05;
    super.p = 5;
    super.i = 0;
    super.d = 0.25;
    super.outputRange = 1;
    super.subsystemName = "Intake";
    super.pidName = "IntakePID";

    intakeAngleSensor = Robot.intakeAngleSensor;
    intakeAngleSensor.putSensorOnLiveWindow(super.subsystemName, "Intake");
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
      output = -output;
      MoveIntakeSafely.moveIntake(output);
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