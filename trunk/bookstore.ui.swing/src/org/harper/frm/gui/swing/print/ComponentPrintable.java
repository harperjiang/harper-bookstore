package org.harper.frm.gui.swing.print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class ComponentPrintable implements Printable {

	private Component component;

	public ComponentPrintable(Component comp) {
		this.component = comp;
	}

	protected transient boolean inited = false;

	private transient int counter = -1;

	private double pageWidth, pageHeight, compWidth, compHeight, emplifyFactor;

	protected void init(PageFormat pageFormat) {
		if (!inited) {
			pageWidth = pageFormat.getImageableWidth();
			pageHeight = pageFormat.getImageableHeight();
			compWidth = component.getPosition().getWidth();
			compHeight = component.getPosition().getHeight();
			emplifyFactor = pageWidth / compWidth;
		}
		inited = true;
	}
	
	

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		// Do not print the first page
		// See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4186111

		counter += 1;
		init(pageFormat);
		double maxPage = Math.ceil(component.getPosition().getHeight()
				/ pageFormat.getHeight());
		if (pageIndex < 0 || pageIndex >= maxPage) {
			counter = -1;
			return Printable.NO_SUCH_PAGE;
		}

		// Determine which part of the image should be drawn

		Graphics2D g2d = (Graphics2D) graphics;

		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		g2d.translate(0, -pageHeight * pageIndex);
		g2d.scale(emplifyFactor, emplifyFactor);
		if (0 == counter % 2) {
		} else {
			component.prepare(g2d);
			component.paint(g2d);
		}
		return Printable.PAGE_EXISTS;
	}

}
