package org.harper.frm.gui.swing.print.border;

import java.awt.Graphics;
import java.awt.Insets;

import org.harper.frm.gui.swing.print.Border;
import org.harper.frm.gui.swing.print.Component;

public class EmptyBorder implements Border {

	private Insets wide;

	public EmptyBorder(int width) {
		this.wide = new Insets(width, width, width, width);
	}

	public EmptyBorder(int top, int left, int bottom, int right) {
		this.wide = new Insets(top, left, bottom, right);
	}

	public Insets getBorderInsets(Component c) {
		return wide;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		// Do nothing
	}

}
