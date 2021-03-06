package org.harper.frm.gui.swing.print.layout;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import org.harper.frm.gui.swing.print.Component;
import org.harper.frm.gui.swing.print.Layout;
import org.harper.frm.gui.swing.print.comp.Container;

public class RowLayout implements Layout {

	private int span = 2;

	public int getSpan() {
		return span;
	}

	public void setSpan(int span) {
		this.span = span;
	}

	public Dimension getPreferredSize(Container container, Graphics2D g2d) {
		Dimension dim = container.getPosition().getSize();
		dim.height = 0;
		for (Component child : container.getChildren()) {
			Dimension prefSize = child.getPreferredSize(g2d);
			dim.height += prefSize.height + span;
		}
		return dim;
	}

	public void layout(Container container, Graphics2D g2d) {
		Rectangle imagearea = container.getImageableArea();
		Point loc = imagearea.getLocation();
		for (Component child : container.getChildren()) {
			child.getPosition().width = imagearea.width;
			Dimension prefer = child.getPreferredSize(g2d);
			child.setPosition(new Rectangle(loc, new Dimension(imagearea.width,
					prefer.height)));
			loc.y += prefer.height + span;
		}

	}
}
