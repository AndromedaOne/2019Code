package frc.robot.subsystems.leds;

import com.typesafe.config.Config;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class LeftLEDs extends LEDs {

  public LeftLEDs() {
    Config conf = Robot.getConfig();

    red = new DigitalOutput(conf.getInt("subsystems.led.leftRed"));
    red.enablePWM(0);
    green = new DigitalOutput(conf.getInt("subsystems.led.leftGreen"));
    green.enablePWM(0);
    blue = new DigitalOutput(conf.getInt("subsystems.led.leftBlue"));
    blue.enablePWM(0);
    setPurple(1.0);
  }

  @Override
  public void updateLEDs() {
    if (Robot.frontLineSensor.findLine().lineFound && (Robot.frontLineSensor.findLine().lineAngle >= -0.4)) {
      blinkCounter++;
      double blinkSpeed = (Robot.drivetrainFrontUltrasonic.getMinDistanceInches() * kBlinkSpeedMultiplier) - 13;
      if (blinkCounter > blinkSpeed) {
        blinkCounter = 0;
        toggleLEDs();
        if (SmartDashboard.getString("Left", "").equals("")) {
          SmartDashboard.putString("Left", "\u26A0");
        } else {
          SmartDashboard.putString("Left", "");
        }
      }

    } else {
      SmartDashboard.putString("Left", "");
      toggleLEDsOn();
    }
  }

  @Override
  protected void initDefaultCommand() {
  }

}