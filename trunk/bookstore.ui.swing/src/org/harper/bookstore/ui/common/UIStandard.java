package org.harper.bookstore.ui.common;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class UIStandard {

	public static Font DEFAULT_FONT = new Font("微软雅黑", Font.PLAIN, 12);

	public static void setDefaultFont() {
		Font defaultFont = DEFAULT_FONT;
		UIManager.put("List.font", defaultFont);
		UIManager.put("TableHeader.font", defaultFont);
		UIManager.put("Panel.font", defaultFont);
		UIManager.put("TextArea.font", defaultFont);
		UIManager.put("ToggleButton.font", defaultFont);
		UIManager.put("ComboBox.font", defaultFont);
		UIManager.put("ScrollPane.font", defaultFont);
		UIManager.put("Spinner.font", defaultFont);
		UIManager.put("RadioButtonMenuItem.font", defaultFont);
		UIManager.put("Slider.font", defaultFont);
		UIManager.put("EditorPane.font", defaultFont);
		UIManager.put("OptionPane.font", defaultFont);
		UIManager.put("ToolBar.font", defaultFont);
		UIManager.put("Tree.font", defaultFont);
		UIManager.put("CheckBoxMenuItem.font", defaultFont);
		UIManager.put("TitledBorder.font", defaultFont);
		UIManager.put("Table.font", defaultFont);
		UIManager.put("MenuBar.font", defaultFont);
		UIManager.put("PopupMenu.font", defaultFont);
		UIManager.put("DesktopIcon.font", defaultFont);
		UIManager.put("Label.font", defaultFont);
		UIManager.put("MenuItem.font", defaultFont);
		UIManager.put("TextField.font", defaultFont);
		UIManager.put("TextPane.font", defaultFont);
		UIManager.put("CheckBox.font", defaultFont);
		UIManager.put("ProgressBar.font", defaultFont);
		UIManager.put("FormattedTextField.font", defaultFont);
		UIManager.put("ColorChooser.font", defaultFont);
		UIManager.put("Menu.font", defaultFont);
		UIManager.put("PasswordField.font", defaultFont);
		UIManager.put("Viewport.font", defaultFont);
		UIManager.put("TabbedPane.font", defaultFont);
		UIManager.put("RadioButton.font", defaultFont);
		UIManager.put("ToolTip.font", defaultFont);
		UIManager.put("Button.font", defaultFont);
	}

	public static void standardFrame(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (screenSize.getWidth() - frame.getWidth()) / 2,
				(int) (screenSize.getHeight() - frame.getHeight()) / 2);
	}

	public static void standardDialog(JDialog frame) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x, y;
		if (null != frame.getParent() && frame.getParent().isVisible()) {
			screenSize = frame.getParent().getSize();
			x = frame.getParent().getX()
					+ ((int) (screenSize.getWidth() - frame.getWidth()) / 2);
			y = frame.getParent().getY()
					+ ((int) (screenSize.getHeight() - frame.getHeight()) / 2);
		} else {
			x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
			y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
		}
		frame.setLocation(x, y);
	}
}
