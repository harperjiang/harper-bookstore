package org.harper.frm.gui.swing.print;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.junit.Test;

public class PrinterTest {

	@Test
	public void testPrint() throws Exception {
		PrinterJob job = PrinterJob.getPrinterJob();
		
		job.setPrintable(new Printable() {

			@Override
			public int print(Graphics graphics, PageFormat pageFormat,
					int pageIndex) throws PrinterException {
				
				if(pageIndex>0)
					return NO_SUCH_PAGE;
				Graphics2D g2d = (Graphics2D)graphics;
			
				
//				g2d.setClip(100,100,100,100);
				
				g2d.drawRect(100, 100, 400, 20);
				
				g2d.setStroke(new BasicStroke(5));
				g2d.drawRect(100, 100, 280, 50);
				g2d.setStroke(new BasicStroke(10));
				g2d.drawRect(100, 100, 100, 100);
				
				return PAGE_EXISTS;
			}
			
		});
		if(job.printDialog()) {
			job.print();
		}
	}
}
