package org.harper.frm.gui.swing.print.layout;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import org.harper.frm.gui.swing.print.Component;
import org.harper.frm.gui.swing.print.Layout;
import org.harper.frm.gui.swing.print.Print;
import org.harper.frm.gui.swing.print.comp.Container;

public class FillLayout implements Layout {

	private int dir;

	public FillLayout() {
		this(Print.VERTICAL);
	}

	public FillLayout(int direction) {
		this.dir = direction;
	}

	public Dimension getPreferredSize(Container container, Graphics2D g2d) {
		return container.getPosition().getSize();
	}

	public void layout(Container container, Graphics2D g2d) {
		Point next = container.getImageableArea().getLocation();
		Rectangle area = container.getImageableArea();
		double xstep = area.getWidth() / container.getChildren().size();
		double ystep = area.getHeight() / container.getChildren().size();

		for (Component child : container.getChildren()) {
			if (Print.VERTICAL == dir) {
				child.setPosition(new Rectangle(next, new Dimension(area.width,
						(int) Math.floor(ystep))));
				next.y += ystep;
			} else if (Print.HORIZONTAL == dir) {
				child.setPosition(new Rectangle(next, new Dimension((int) Math
						.floor(xstep), area.height)));
				next.x += xstep;
			}
		}
	}
}
