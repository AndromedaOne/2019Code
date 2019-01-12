package frc.robot.utilities;

import edu.wpi.first.wpilibj.Joystick;

public enum EnumeratedRawAxis {
	LEFTSTICKHORIZONTAL(0),
	LEFTSTICKVERTICAL(1),
	LEFTTRIGGER(2),
	RIGHTTRIGGER(3),
	RIGHTSTICKHORIZONTAL(4),
	RIGHTSTICKVERTICAL(5);

	private int m_rawAxisValue;

	private EnumeratedRawAxis(int value) {
		m_rawAxisValue = value;
	}

	public int getValue() {
		return m_rawAxisValue;
	}

	public double getRawAxis(Joystick gamepad) {
		return gamepad.getRawAxis(getValue());
	}

	public static double getLeftStickHorizontal (Joystick gamepad) {
		return LEFTSTICKHORIZONTAL.getRawAxis(gamepad);
	}

	public static double getLeftStickVertical (Joystick gamepad) {
		return LEFTSTICKVERTICAL.getRawAxis(gamepad);
	}

	public static boolean getLeftTriggerButton(Joystick gamepad) {
		return LEFTTRIGGER.getRawAxis(gamepad)>0.5;
	}

	public static double getLeftTriggerValue(Joystick gamepad) {
		return LEFTTRIGGER.getRawAxis(gamepad);
	}

	public static boolean getRightTriggerButton(Joystick gamepad) {
		return RIGHTTRIGGER.getRawAxis(gamepad)>0.5;
	}

	public static double getRightTriggerValue(Joystick gamepad) {
		return RIGHTTRIGGER.getRawAxis(gamepad);
	}

	public static double getRightStickHorizontal (Joystick gamepad) {
		return RIGHTSTICKHORIZONTAL.getRawAxis(gamepad);
	}

	public static double getRightStickVertical (Joystick gamepad) {
		return RIGHTSTICKVERTICAL.getRawAxis(gamepad);
	}
}