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

	private transient int counter = -1;
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		// Do not print the first page
		// See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4186111
		
		counter += 1;
		
		double maxPage = Math.ceil(component.getPosition().getHeight()
				/ pageFormat.getHeight());
		if (pageIndex < 0 || pageIndex >= maxPage) {
			counter = -1;
			return Printable.NO_SUCH_PAGE;
		}
		Graphics2D g2d = (Graphics2D) graphics;

		double pageWidth = pageFormat.getImageableWidth();
		double pageHeight = pageFormat.getImageableHeight();
		double compWidth = component.getPosition().getWidth();
		double compHeight = component.getPosition().getHeight();

		double emplifyFactor = Math.min(pageWidth / compWidth, pageHeight
				/ compHeight);

		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		g2d.scale(emplifyFactor, emplifyFactor);
		if(0 == counter % 2)
			component.prepare(g2d);
		else
			component.paint(g2d);
		return Printable.PAGE_EXISTS;
	}

}
