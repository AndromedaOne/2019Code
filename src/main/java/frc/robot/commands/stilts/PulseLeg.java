package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PulseLeg extends Command {

  public enum stiltLeg {
    FRONTRIGHT, FRONTLEFT, REARLEFT, REARRIGHT, STOPLEGS
  }

  private stiltLeg currentLeg = stiltLeg.STOPLEGS;
  private long initTime = 0;
  private long kHoldTime = 10;
  private long currentHoldTime = 0;
  private boolean done = false;

  public PulseLeg(stiltLeg leg) {
    currentLeg = leg;
  }

  public void initialize() {
    initTime = System.currentTimeMillis();
    currentHoldTime = initTime + kHoldTime;
    System.out.println("initTime: " + initTime);
    done = false;
  }

  public void execute() {
    long time = System.currentTimeMillis();
    System.out.println("time: " + time);
    switch (currentLeg) {
    case FRONTLEFT:
      Robot.pneumaticStilts.extendFrontLeft();
      currentLeg = stiltLeg.STOPLEGS;
    case FRONTRIGHT:
      Robot.pneumaticStilts.extendFrontRight();
      currentLeg = stiltLeg.STOPLEGS;
    case REARLEFT:
      Robot.pneumaticStilts.extendRearLeft();
      currentLeg = stiltLeg.STOPLEGS;
    case REARRIGHT:
      Robot.pneumaticStilts.extendRearRight();
      currentLeg = stiltLeg.STOPLEGS;
    case STOPLEGS:
      if (currentHoldTime < time) {
        Robot.pneumaticStilts.stopAllLegs();
        done = true;
        break;
      }
    }
  }

  @Override
  protected boolean isFinished() {
    return done;
  }
}