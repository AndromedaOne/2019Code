package frc.robot.commands;

//import com.sun.tools.classfile.StackMapTable_attribute.append_frame;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.sensors.NavXGyroSensor;
import frc.robot.subsystems.pneumaticstilts.PneumaticStilts;
import frc.robot.subsystems.pneumaticstilts.RealPneumaticStilts;
import frc.robot.telemetries.Trace;
import frc.robot.telemetries.TracePair;

public class RaiseRobot extends Command {

  public enum StiltPulsed {
    FRONTRIGHT, FRONTLEFT, REARLEFT, REARRIGHT, ALLFOUR
  }
  public enum StiltsStates {
    CALCULATING, PULSING, WAITING
  }

  private NavXGyroSensor gyro = NavXGyroSensor.getInstance();
  private PneumaticStilts pneumaticStilts = Robot.pneumaticStilts;
  private double initialXValue = 0;
  private double initialYValue = 0;
  private double frontLeftError = 1;
  private double frontRightError = 1;
  private double rearLeftError = 1;
  private double rearRightError = 1;
  private double p = 0.1;
  private long waitTime = 0;
  
  private double angleYThreshold = 0.5;
  private double angleXThreshold = 0.5;

  private StiltsStates stiltsStates = StiltsStates.CALCULATING;
  private StiltPulsed previousStiltPulsed = StiltPulsed.ALLFOUR;
  private long initTime;
  private long originalPulseTime = 2;
  private long kPulseTime = originalPulseTime;
  private long kWaitTime = 100;
  private long currentPulseTime = 0;


  public void initialize() {
    initialXValue = gyro.getXAngle();
    initialYValue = gyro.getYAngle();
  }

  private double calculateError(double x, double y) {
    double error = 1 - (p * (x + y));
    /*if(error > 1) {
      error = 1;
    }
    if (error < 0) {
      error = 0;
    }*/
    return error; 
  }

  public void execute() {

    double angleY = gyro.getYAngle() - initialYValue;
    double angleX = gyro.getXAngle() - initialXValue;
    frontLeftError = rearLeftError = frontRightError = rearRightError = 1;


    // Left Front Leg
    //if ((angleY < 0) || (angleX < 0)) {
      frontLeftError = calculateError(-angleX, -angleY);
    //}

    // Left Rear Leg
    //if ((angleY > 0) || (angleX < 0)) {
      rearLeftError = calculateError(-angleX, angleY);
    //}

    // Right Front Leg
    //if ((angleY < 0) || (angleX > 0)) {
      frontRightError = calculateError(angleX, -angleY);
    //}

    // Right Rear Leg
    //if ((angleY > 0) || (angleX > 0)) {
      rearRightError = calculateError(angleX, angleY);
    //}

    Trace.getInstance().addTrace(true, "PneumaticStilts",
        new TracePair("Angle X", angleX),
        new TracePair("Angle Y", angleY),
        new TracePair("Front Left Leg", frontLeftError),
        new TracePair("Front Right Leg", frontRightError),
        new TracePair("Rear Left Leg", rearLeftError),
        new TracePair("Rear Right Leg", rearRightError),
        new TracePair("kPulseTime", (double)kPulseTime));

    //pneumaticStilts.stabilizedMove(frontLeftError, frontRightError, rearLeftError, rearRightError);
    long time = System.currentTimeMillis();
    switch (stiltsStates){
      case CALCULATING:
        
        if(Math.abs(angleY) < angleYThreshold && Math.abs(angleX) < angleXThreshold) {
          pneumaticStilts.extendFrontLeft();
          pneumaticStilts.extendFrontRight();
          pneumaticStilts.extendRearLeft();
          pneumaticStilts.extendRearRight();
          kPulseTime = originalPulseTime;
          previousStiltPulsed = StiltPulsed.ALLFOUR;
        }else {
          if (isMost(frontLeftError, rearLeftError, frontRightError, rearRightError)) {
            pneumaticStilts.extendFrontLeft();
            if(previousStiltPulsed == StiltPulsed.FRONTLEFT) {
              kPulseTime *=1.5;
            }else {
              kPulseTime = originalPulseTime;
            }
            previousStiltPulsed = StiltPulsed.FRONTLEFT;
          }else if (isMost(rearLeftError, frontLeftError, frontRightError, rearRightError)) {
            pneumaticStilts.extendRearLeft();
            if(previousStiltPulsed == StiltPulsed.REARLEFT) {
              kPulseTime *=1.5;
            }else {
              kPulseTime = originalPulseTime;
            }
            previousStiltPulsed = StiltPulsed.REARLEFT;

          }else if (isMost(frontRightError, rearLeftError, frontLeftError, rearRightError)) {
            pneumaticStilts.extendFrontRight();
            if(previousStiltPulsed == StiltPulsed.FRONTRIGHT) {
              kPulseTime *=1.5;
            }else {
              kPulseTime = originalPulseTime;
            }
            previousStiltPulsed = StiltPulsed.FRONTRIGHT;

          }else {
            pneumaticStilts.extendRearRight();
            if(previousStiltPulsed == StiltPulsed.REARRIGHT) {
              kPulseTime *=1.5;
            }else {
              kPulseTime = originalPulseTime;
            }
            previousStiltPulsed = StiltPulsed.REARRIGHT;
          }
        }

        initTime = System.currentTimeMillis();
        currentPulseTime = initTime + kPulseTime;
        stiltsStates = StiltsStates.PULSING;
        
        break;
      case PULSING:
        if(currentPulseTime < time) {
            pneumaticStilts.stopAllLegs();
            stiltsStates = StiltsStates.WAITING;
            waitTime = time + kWaitTime;
        }
      case WAITING:
        if(time > waitTime){
          stiltsStates = StiltsStates.CALCULATING;
        }
        
    }
  }

  private boolean isMost(double numberToTest, double... others) {
    for(double a : others) {
      if(numberToTest < a){
        return false;
      }
    }
    return true;
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  public void end() {

  }

}