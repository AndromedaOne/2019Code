package frc.robot.utilities;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
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

//These GetNButtonStatus all return booleans (not objects) based on if the buttons are pressed or not

	public static boolean getAButtonStatus(Joystick gamepad) {
		return ABUTTON.isPressed(gamepad);
	}

	public static boolean getBButtonStatus(Joystick gamepad) {
		return BBUTTON.isPressed(gamepad);
	}

	public static boolean getXButtonStatus(Joystick gamepad) {
		return XBUTTON.isPressed(gamepad);
	}

	public static boolean getYButtonStatus(Joystick gamepad) {
		return YBUTTON.isPressed(gamepad);
	}

	public static boolean getLeftButtonStatus(Joystick gamepad) {
		return LEFTBUMPERBUTTON.isPressed(gamepad);
	}

	public static boolean getRightButtonStatus(Joystick gamepad) {
		return RIGHTBUMPERBUTTON.isPressed(gamepad);
	}

	public static boolean getBackButtonStatus(Joystick gamepad) {
		return BACKBUTTON.isPressed(gamepad);
	}

	public static boolean getStartButtonStatus(Joystick gamepad) {
		return STARTBUTTON.isPressed(gamepad);
	}

	public static boolean getLeftStickButtonStatus(Joystick gamepad) {
		return LEFTSTICKBUTTON.isPressed(gamepad);
	}

	public static boolean getRightStickButtonStatus(Joystick gamepad) {
		return RIGHTSTICKBUTTON.isPressed(gamepad);
	}
	public static boolean getLeftTriggerButtonStatus(Joystick gamepad) {
		return LEFTTRIGGER.isPressed(gamepad);
	}

	//These will return objects based on if the button is pressed

	public static ButtonsEnumerated getAButton(Joystick gamepad) {
		return ABUTTON;
	}

	public static ButtonsEnumerated getBButton(Joystick gamepad) {
		return BBUTTON;
	}

	public static ButtonsEnumerated getXButton(Joystick gamepad) {
		return XBUTTON;
	}

	public static ButtonsEnumerated getYButton(Joystick gamepad) {
		return YBUTTON;
	}

	//This is the button we want to pass into OI for now
	public static ButtonsEnumerated getLeftButton(Joystick gamepad) {
		return LEFTBUMPERBUTTON;
	}

	public static ButtonsEnumerated getRightButton(Joystick gamepad) {
		return RIGHTBUMPERBUTTON;
	}

	public static ButtonsEnumerated getBackButton(Joystick gamepad) {
		return BACKBUTTON;
	}

	public static ButtonsEnumerated getStartButton(Joystick gamepad) {
		return STARTBUTTON;
	}

	public static ButtonsEnumerated getLeftStickButton(Joystick gamepad) {
		return LEFTSTICKBUTTON;
	}

	public static ButtonsEnumerated getRightStickButton(Joystick gamepad) {
		return RIGHTSTICKBUTTON;
	}
	public static ButtonsEnumerated getLeftTriggerButton(Joystick gamepad) {
		return LEFTTRIGGER;
	}
}