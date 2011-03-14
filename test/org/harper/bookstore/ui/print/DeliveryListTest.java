package org.harper.bookstore.ui.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.junit.Test;

public class DeliveryListTest {

	@Test
	public void testDeliveryList() throws PrinterException {
		DeliveryListLayout dl = new DeliveryListLayout();
		
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(dl));
		if(job.printDialog())
			job.print();
	}

}
