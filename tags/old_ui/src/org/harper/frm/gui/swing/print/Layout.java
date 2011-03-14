package org.harper.frm.gui.swing.print;

import java.awt.Dimension;
import java.awt.Graphics2D;

import org.harper.frm.gui.swing.print.comp.Container;

public interface Layout {

	public Dimension getPreferredSize(Container container, Graphics2D g2d);

	public void layout(Container container, Graphics2D g2d);
}
