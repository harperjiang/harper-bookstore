package org.harper.frm.gui.swing.print;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;

public abstract class Component {

	private Rectangle position = new Rectangle(0, 0, 0, 0);

	private Color color = Color.BLACK;

	private Font font = Print.DEFAULT_FONT;

	private Dimension preferredSize;

	private Border border;

	public Rectangle getPosition() {
		return position;
	}

	public void setPosition(Rectangle position) {
		this.position = position;
	}

	public Dimension getPreferredSize(Graphics2D g2d) {
		if (null == preferredSize) {
			preferredSize = calcPreferredSize(g2d);
			if (null != getBorder()) {
				Insets ins = getBorder().getBorderInsets(this);
				preferredSize = new Dimension(preferredSize.width + ins.left
						+ ins.right, preferredSize.height + ins.top
						+ ins.bottom);
			}
		}
		return preferredSize;
	}

	public void setPreferredSize(Dimension preferredSize) {
		this.preferredSize = preferredSize;
	}

	public abstract Dimension calcPreferredSize(Graphics2D g2d);

	public void prepare(Graphics2D g2d) {
		Color oldColor = g2d.getColor();
		Font oldFont = g2d.getFont();
		Stroke oldStroke = g2d.getStroke();
		if (null != getFont())
			g2d.setFont(getFont());
		if (null != getColor())
			g2d.setColor(getColor());
		getPreferredSize(g2d);
		g2d.setFont(oldFont);
		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);
	}

	public void paint(Graphics2D g2d) {
		Color oldColor = g2d.getColor();
		Font oldFont = g2d.getFont();
		Stroke oldStroke = g2d.getStroke();
		if (null != getFont())
			g2d.setFont(getFont());
		if (null != getColor())
			g2d.setColor(getColor());
		Shape oldClip = g2d.getClip();
		g2d.setClip(getPosition());
		// Point imagePoint = getImageableArea().getLocation();
		g2d.translate(getPosition().x, getPosition().y);
		draw(g2d);
		if (null != getBorder())
			getBorder().paintBorder(this, g2d, 0, 0, getPosition().width,
					getPosition().height);
		g2d.translate(-getPosition().x, -getPosition().y);
		
		g2d.setClip(oldClip);
		g2d.setFont(oldFont);
		g2d.setColor(oldColor);
		g2d.setStroke(oldStroke);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public Rectangle getImageableArea() {
		if (null != getBorder()) {
			Insets ins = getBorder().getBorderInsets(this);
			return new Rectangle(ins.left, ins.top, getPosition().width
					- ins.left - ins.right, getPosition().height - ins.top
					- ins.bottom);
		}

		return new Rectangle(0, 0, getPosition().width, getPosition().height);
	}

	public abstract void draw(Graphics2D g2d);
}
