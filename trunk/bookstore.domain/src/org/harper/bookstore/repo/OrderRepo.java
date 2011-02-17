package org.harper.bookstore.repo;

import java.util.Date;
import java.util.List;

import org.harper.bookstore.domain.deliver.DeliveryOrder;
import org.harper.bookstore.domain.order.ListPrice;
import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.bookstore.domain.profile.Book;

public interface OrderRepo extends Repo {

	public List<SupplyOrder> getSupplyOrder(Book book);

	public List<Order> searchOrder(String number, String type, Date start,
			Date stop, int[] status, int[] expressStatus, String partyId);

	public PurchaseOrder getPurchaseOrderByRefno(String refno);

	public ListPrice getListPrice(Book book);

	public List<PurchaseOrder> getExternalOutstandingOrder();

	public List<DeliveryOrder> searchDeliveryOrder(Date fromDate, Date toDate,
			String poNumber, String consigneeName, String poCustomerId);
}
