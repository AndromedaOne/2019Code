package frc.robot.subsystems.leds;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.Robot;

public class RightLEDs extends LEDs {

  public RightLEDs() {
    Config conf = Robot.getConfig();
    red = new DigitalOutput(conf.getInt("subsystems.led.rightRed"));
    red.enablePWM(0);
    green = new DigitalOutput(conf.getInt("subsystems.led.rightGreen"));
    green.enablePWM(0);
    blue = new DigitalOutput(conf.getInt("subsystems.led.rightBlue"));
    blue.enablePWM(0);
    setPurple(1.0);
  }

  @Override
  public void updateLEDs() {
    if (Robot.frontLineSensor.findLine().lineFound) {
      blinkCounter++;
      if (blinkCounter > Robot.drivetrainFrontUltrasonic.getDistanceInches() * kBlinkSpeedMultiplier) {
        toggleLEDs();
      }
    } else {
      toggleLEDsOn();
    }
  }

  @Override
  protected void initDefaultCommand() {

  }

}