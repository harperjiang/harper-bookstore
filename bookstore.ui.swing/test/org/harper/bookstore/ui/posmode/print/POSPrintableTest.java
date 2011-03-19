package org.harper.bookstore.ui.posmode.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.posmode.POSBookItem;
import org.harper.bookstore.ui.posmode.POSModeBean;
import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.junit.Test;

public class POSPrintableTest {

	@Test
	public void testPOSPrintable() throws PrinterException {
		POSPrintable pp = new POSPrintable();
		
		POSModeBean bean = new POSModeBean();
		bean.setTradeId("42342323123412");
		for(int i = 0;i<10;i++) {
			Book book = new Book();
			book.setIsbn("978131213133"+i);
			book.setName("这是一本书"+i);
			POSBookItem item = new POSBookItem();
			item.setBook(book);
			item.setCount(i);
			bean.addItem(item);
		}
		
		pp.setSource(bean);
		
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(pp));
		if (job.printDialog())
			job.print();
	}

}
