package org.harper.frm.gui.swing.print;

import java.awt.Graphics;
import java.awt.Insets;

public interface Border {

	void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height);

	Insets getBorderInsets(Component c);
}
