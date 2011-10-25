package org.harper.frm.gui.swing.print.layout;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.harper.frm.gui.swing.print.Component;
import org.harper.frm.gui.swing.print.Layout;
import org.harper.frm.gui.swing.print.comp.Container;

public class ExpandLayout implements Layout {

	public Dimension getPreferredSize(Container container, Graphics2D g2d) {
		Dimension current = container.getPreferredSize(g2d);
		for (Component child : container.getChildren()) {
			Dimension childDim = child.getPreferredSize(g2d);
			current.width = Math.max(child.getPosition().x + childDim.width,
					current.width);
			current.height = Math.max(child.getPosition().y + childDim.height,
					current.height);
		}
		return current;
	}

	public void layout(Container container, Graphics2D g2d) {
		for (Component child : container.getChildren()) {
			Dimension childDim = child.getPreferredSize(g2d);
			child.setPosition(new Rectangle(child.getPosition().x, child
					.getPosition().y, childDim.width, childDim.height));
		}
	}

}
