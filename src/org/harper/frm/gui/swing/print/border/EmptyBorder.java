package org.harper.frm.gui.swing.print.border;

import java.awt.Graphics;
import java.awt.Insets;

import org.harper.frm.gui.swing.print.Border;
import org.harper.frm.gui.swing.print.Component;

public class EmptyBorder implements Border {

	private int width;

	public EmptyBorder(int width) {
		this.width = width;
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(width, width, width, width);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		// Do nothing
	}

}
