package org.harper.bookstore.domain.order;

import static org.junit.Assert.assertEquals;

import org.harper.bookstore.domain.order.PurchaseOrder.DeliveryStatus;
import org.harper.bookstore.domain.profile.Book;
import org.junit.Test;

public class PurchaseOrderTest {

	@Test
	public void testMakeDelivery() {
		PurchaseOrder po = new PurchaseOrder();

		po.makeDelivery();
		assertEquals(DeliveryStatus.FULLY_SENT.ordinal(),
				po.getDeliveryStatus());

		po = new PurchaseOrder();
		
		Book book1 = new Book();
		book1.setIsbn("001");
		book1.setName("001");
		
		Book book2 = new Book();
		book2.setIsbn("002");
		book2.setName("002");
		
		OrderItem item1 = new OrderItem();
		item1.setBook(book1);
		item1.setCount(5);
		item1.setSentCount(0);

		OrderItem item2 = new OrderItem();
		item2.setCount(5);
		item2.setBook(book2);
		item2.setSentCount(0);

		po.addItem(item1);
		po.addItem(item2);

		po.makeDelivery();
		assertEquals(DeliveryStatus.NOT_SENT.ordinal(), po.getDeliveryStatus());

		item1.setSentCount(3);
		po.makeDelivery();
		assertEquals(DeliveryStatus.PARTIAL_SENT.ordinal(),
				po.getDeliveryStatus());

		item1.setSentCount(5);
		po.makeDelivery();
		assertEquals(DeliveryStatus.PARTIAL_SENT.ordinal(),
				po.getDeliveryStatus());

		item2.setSentCount(3);
		po.makeDelivery();
		assertEquals(DeliveryStatus.PARTIAL_SENT.ordinal(),
				po.getDeliveryStatus());

		item2.setSentCount(5);
		po.makeDelivery();
		assertEquals(DeliveryStatus.FULLY_SENT.ordinal(),
				po.getDeliveryStatus());
	}

}
