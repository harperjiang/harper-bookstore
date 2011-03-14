package org.harper.bookstore.ui.order;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrinterTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new Printable() {
			@Override
			public int print(Graphics graphics, PageFormat pageFormat,
					int pageIndex) throws PrinterException {
				if (pageIndex > 0) {
					return (NO_SUCH_PAGE);
				} else {
					Graphics2D g2d = (Graphics2D) graphics;
					g2d.translate(pageFormat.getImageableX(), pageFormat
							.getImageableY());
					// Turn off double buffering
					g2d.drawString("100,100", 100,100 );
					g2d.drawLine(10, 10, 100, 10);
					g2d.drawLine(100, 10, 100, 100);
					// Turn double buffering back on
					return (PAGE_EXISTS);
				}

			}
		});

		if (job.printDialog()) {
			job.print();
		}
	}

}
