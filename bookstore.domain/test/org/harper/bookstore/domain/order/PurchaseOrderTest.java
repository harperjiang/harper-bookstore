package org.harper.bookstore.domain.order;

import static org.junit.Assert.assertEquals;

import org.harper.bookstore.domain.order.PurchaseOrder.DeliveryStatus;
import org.junit.Test;

public class PurchaseOrderTest {

	@Test
	public void testMakeDelivery() {
		PurchaseOrder po = new PurchaseOrder();
		
		OrderItem item1 = new OrderItem();
		item1.setCount(5);
		item1.setSentCount(0);
		
		OrderItem item2 = new OrderItem();
		item2.setCount(5);
		item2.setSentCount(0);
		
		po.addItem(item1);
		po.addItem(item2);
		
		po.makeDelivery();
		assertEquals(DeliveryStatus.NOT_SENT.ordinal(),po.getDeliveryStatus());
		
		item1.setSentCount(3);
		po.makeDelivery();
		assertEquals(DeliveryStatus.PARTIAL_SENT.ordinal(),po.getDeliveryStatus());
		
		item1.setSentCount(5);
		po.makeDelivery();
		assertEquals(DeliveryStatus.PARTIAL_SENT.ordinal(),po.getDeliveryStatus());
		
		item2.setSentCount(3);
		po.makeDelivery();
		assertEquals(DeliveryStatus.PARTIAL_SENT.ordinal(),po.getDeliveryStatus());
		

		item2.setSentCount(5);
		po.makeDelivery();
		assertEquals(DeliveryStatus.FULLY_SENT.ordinal(),po.getDeliveryStatus());
	}

}
