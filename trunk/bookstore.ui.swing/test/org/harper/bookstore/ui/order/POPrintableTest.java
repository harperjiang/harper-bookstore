package org.harper.bookstore.ui.order;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.ui.order.print.POContainer;
import org.junit.Test;

public class POPrintableTest {

	@Test
	public void testPrint() throws PrinterException {
		PurchaseOrder order = new PurchaseOrder();
		
		Customer cust = new Customer();
		cust.setId("new Customer");
		
		order.setCustomer(cust);
		
		order.setCreateDate(new Date());
		
		Book book1 = new Book();
		book1.setIsbn("9781234512345");
		book1.setName("This is a Book");
		
		List<OrderItem> items = new ArrayList<OrderItem>();
		OrderItem item1 = new OrderItem();
		item1.setBook(book1);
		item1.setCount(4);
		item1.setUnitPrice(new BigDecimal(120));
		
		items.add(item1);
		
		order.setItems(items);

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new POContainer(order));

		if (job.printDialog()) {
			job.print();
		}
	}

}
