package frc.robot.utilities;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public enum ButtonsEnumerated {
	ABUTTON(1),
	BBUTTON(2),
	XBUTTON(3),
	YBUTTON(4),
	LEFTBUMPERBUTTON(5),
	RIGHTBUMPERBUTTON(6),
	BACKBUTTON(7),
	STARTBUTTON(8),
	LEFTSTICKBUTTON(9),
	RIGHTSTICKBUTTON(10),
	LEFTTRIGGER(2) {
		public boolean isPressed(Joystick gamepad) {
			return EnumeratedRawAxis.getRightTriggerButton(gamepad);
		}
	},
	RIGHTTRIGGER(3) {
		public boolean isPressed(Joystick gamepad) {
			return EnumeratedRawAxis.getRightTriggerButton(gamepad);
		}
	};
	public int getValue( ) {
		return m_buttonValue;
	}
	

	private int m_buttonValue;

	private ButtonsEnumerated(int value) {
		m_buttonValue = value;
	}

	public JoystickButton getJoystickButton(Joystick driveController) {
		return new JoystickButton(driveController, m_buttonValue);
	}

	public boolean isPressed(Joystick gamepad) {
		return gamepad.getRawButton(m_buttonValue);
	}

	public static boolean getAButton(Joystick gamepad) {
		return ABUTTON.isPressed(gamepad);
	}

	public static boolean getBButton(Joystick gamepad) {
		return BBUTTON.isPressed(gamepad);
	}

	public static boolean getXButton(Joystick gamepad) {
		return XBUTTON.isPressed(gamepad);
	}

	public static boolean getYButton(Joystick gamepad) {
		return YBUTTON.isPressed(gamepad);
	}

	public static boolean getLeftButton(Joystick gamepad) {
		return LEFTBUMPERBUTTON.isPressed(gamepad);
	}

	public static boolean getRightButton(Joystick gamepad) {
		return RIGHTBUMPERBUTTON.isPressed(gamepad);
	}

	public static boolean getBackButton(Joystick gamepad) {
		return BACKBUTTON.isPressed(gamepad);
	}

	public static boolean getStartButton(Joystick gamepad) {
		return STARTBUTTON.isPressed(gamepad);
	}

	public static boolean getLeftStickButton(Joystick gamepad) {
		return LEFTSTICKBUTTON.isPressed(gamepad);
	}

	public static boolean getRightStickButton(Joystick gamepad) {
		return RIGHTSTICKBUTTON.isPressed(gamepad);
	}
	public static boolean getLeftTriggerButton(Joystick gamepad) {
		return LEFTTRIGGER.isPressed(gamepad);
	}
}