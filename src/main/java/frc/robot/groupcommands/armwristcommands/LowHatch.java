package frc.robot.groupcommands.armwristcommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.armwristcommands.ExtendArm;
import frc.robot.commands.armwristcommands.RotateShoulder;
import frc.robot.commands.armwristcommands.RotateWrist;

public class LowHatch extends CommandGroup {

  public LowHatch(boolean positiveShoulder, boolean sameSidePlacement) {
    double directionFactor = positiveShoulder?1:-1;
    
    if(sameSidePlacement){
      addSequential(new RotateWrist(-95.5 * directionFactor));
      addSequential(new ExtendArm(11.88));
      addSequential(new RotateShoulder(4 * directionFactor));

    }else{
      addSequential(new ExtendArm(20));
      addSequential(new RotateWrist(-95.5 * -directionFactor));
      addSequential(new ExtendArm(11.88));
      addSequential(new RotateShoulder(4 * -directionFactor));
    }
  }
}