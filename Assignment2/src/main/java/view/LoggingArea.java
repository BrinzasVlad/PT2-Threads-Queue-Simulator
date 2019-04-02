package view;

import javax.swing.JTextArea;

import controller.LogDisplay;

public class LoggingArea extends JTextArea implements LogDisplay {
	/**
	 * Default-generated serialVersionUID
	 */
	private static final long serialVersionUID = -3479497854838973013L;

	public void addTextLine(String text) {
		setText(getText() + "\n" + text);
	}
}
