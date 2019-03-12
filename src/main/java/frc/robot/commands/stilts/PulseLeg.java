package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.telemetries.Trace;

public class PulseLeg extends Command {

  public enum stiltLeg {
    FRONTLEGS, REARLEGS, STOPLEGS
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
    Trace.getInstance().logCommandStart("PulseLeg");
    initTime = System.currentTimeMillis();
    currentHoldTime = initTime + kHoldTime;
    System.out.println("initTime: " + initTime);
    done = false;
  }

  public void execute() {
    long time = System.currentTimeMillis();
    System.out.println("time: " + time);
    switch (currentLeg) {
    case FRONTLEGS:
      Robot.pneumaticStilts.extendFrontLegs();
      currentLeg = stiltLeg.STOPLEGS;
    case REARLEGS:
      Robot.pneumaticStilts.extendRearLegs();
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
    Trace.getInstance().logCommandStop("PulseLeg");
    return done;
  }
}