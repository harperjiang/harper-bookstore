package org.harper.bookstore.ui.common;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class UIStandard {

	public static void setDefaultFont() {
		Font defaultFont = new Font("宋体", Font.PLAIN, 12);
		UIManager.put("Button.font", defaultFont);
		UIManager.put("Label.font", defaultFont);
	}

	public static void standardFrame(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (screenSize.getWidth() - frame.getWidth()) / 2,
				(int) (screenSize.getHeight() - frame.getHeight()) / 2);
	}

	public static void standardDialog(JDialog frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x, y;
		if (null != frame.getParent()) {
			screenSize = frame.getParent().getSize();
			x = frame.getParent().getX()
					+ ((int) (screenSize.getWidth() - frame.getWidth()) / 2);
			y = frame.getParent().getY()
					+ ((int) (screenSize.getHeight() - frame.getHeight()) / 2);
		} else {
			x = ((int) (screenSize.getWidth() - frame.getWidth()) / 2);
			y = ((int) (screenSize.getHeight() - frame.getHeight()) / 2);
		}
		frame.setLocation(x, y);
	}
}
