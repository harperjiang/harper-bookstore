package org.harper.frm.gui.swing.print.border;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;

import org.harper.frm.gui.swing.print.Border;
import org.harper.frm.gui.swing.print.Component;

public class LineBorder implements Border {
	private float lineWidth;

	public LineBorder(float width) {
		this.lineWidth = width;
	}

	public Insets getBorderInsets(Component c) {
		int up = (int) Math.ceil(lineWidth);
		return new Insets(up, up, up, up);
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		Graphics2D g2d = (Graphics2D) g;
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(this.lineWidth));
		g2d.drawRect(x + (int) Math.ceil(lineWidth / 2),
				y + (int) Math.ceil((float) lineWidth / 2),
				(int) (width - lineWidth), (int) (height - lineWidth));
		g2d.setStroke(oldStroke);
	}
}
