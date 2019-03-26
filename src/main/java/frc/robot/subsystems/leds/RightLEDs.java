package frc.robot.subsystems.leds;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.Robot;

public class RightLEDs extends LEDs {

  public RightLEDs() {
    Config conf = Robot.getConfig();

    red = new DigitalOutput(conf.getInt("subsystems.led.red"));
    red.enablePWM(0);
    System.out.println("Red Port: " + conf.getInt("subsystems.led.red"));
    green = new DigitalOutput(conf.getInt("subsystems.led.green"));
    green.enablePWM(0);
    blue = new DigitalOutput(conf.getInt("subsystems.led.blue"));
    blue.enablePWM(0);
    System.out.println("Set Purple");
    setPurple(1.0);
  }

  @Override
  protected void initDefaultCommand() {

  }

}