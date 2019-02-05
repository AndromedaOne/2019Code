package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class PulseLeg extends Command {

  public enum stiltLeg {
    FRONTRIGHT, FRONTLEFT, REARLEFT, REARRIGHT
  }

  private stiltLeg currentLeg = stiltLeg.FRONTLEFT;
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
    currentLeg.extendLeg();
    done = false;
  }

  public void execute() {
    long time = System.currentTimeMillis();
    System.out.println("time: " + time);
    if (currentHoldTime < time) {
      currentLeg.stopLeg();
      System.out.println("Stopping");
      done = true;
    }
  }

  @Override
  protected boolean isFinished() {
    return done;
  }
}