package frc.robot.commands.stilts;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PulseLeg extends Command {

  public enum StiltLeg {
    FRONTLEGS, REARLEGS, STOPLEGS
  }

  private StiltLeg currentLeg = StiltLeg.STOPLEGS;
  private long initTime = 0;
  private long kHoldTime = 10;
  private long currentHoldTime = 0;
  private boolean done = false;

  public PulseLeg(StiltLeg leg) {
    currentLeg = leg;
    System.out.println("Pulsing legset: " + leg.toString());
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
    case FRONTLEGS:
      Robot.pneumaticStilts.extendFrontLegs();
      currentLeg = StiltLeg.STOPLEGS;
    case REARLEGS:
      Robot.pneumaticStilts.extendRearLegs();
      currentLeg = StiltLeg.STOPLEGS;
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