package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class LoadingStation extends CommandGroup {

  public LoadingStation(boolean positiveShoulder, boolean sameSidePlacement) {
    double directionFactor = positiveShoulder?1:-1;
    
    if(sameSidePlacement){
      addSequential(new RotateWrist(122.11*directionFactor));
      addSequential(new ExtendArm(26.75));
      addSequential(new RotateShoulder(11.75*directionFactor));
    }else {
      addSequential(new ExtendArm(20.0));
      addSequential(new RotateWrist(122.11*-directionFactor));
      addSequential(new ExtendArm(26.75));
      addSequential(new RotateShoulder(11.75*-directionFactor));
    }
  }
}