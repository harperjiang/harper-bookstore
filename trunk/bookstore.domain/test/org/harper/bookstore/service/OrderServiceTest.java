package org.harper.bookstore.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Customer;
import org.harper.bookstore.domain.profile.Supplier;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.frm.toplink.SessionManager;
import org.junit.Test;

public class OrderServiceTest {

	@Test
	public void testSavePurchaseOrder() {

		PurchaseOrder order = new PurchaseOrder();

		Customer cust = new Customer();
		cust.setSource("TAOBAO");
		cust.setId("new Customer");

		order.setCustomer(cust);
		order.setNumber("P0000000001");

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

		new OrderService().savePurchaseOrder(order);
	}

	@Test
	public void testSaveSupplyOrder() {
		
		Book book = new ProfileService().getBook("0001");
		StoreSite site = new StoreSiteService().getAvailableSite(true).get(0);
		
		SupplyOrder so = new SupplyOrder();
		
		so.setSite(site);
		OrderItem item = new OrderItem();
		item.setBook(book);
		item.setCount(10);
		item.setUnitPrice(BigDecimal.TEN);
		
		List<OrderItem> items = new ArrayList<OrderItem>();
		items.add(item);
		so.setItems(items);
		
		Supplier sp = new Supplier();
		sp.setId("test sp");
		
		so.setSupplier(sp);
		
		new OrderService().saveSupplyOrder(so);
	}
	
	@Test
	public void testSaveSupplyOrder2() {
		
		Book book = new ProfileService().getBook("0001");
		StoreSite site = new StoreSite();
		site.setName("Test Site 001");
		
		SupplyOrder so = new SupplyOrder();
		so.setNumber("SO0001");
		
		so.setSite(site);
		OrderItem item = new OrderItem();
		item.setBook(book);
		item.setCount(10);
		item.setUnitPrice(BigDecimal.TEN);
		
		List<OrderItem> items = new ArrayList<OrderItem>();
		items.add(item);
		so.setItems(items);
		
		Supplier sp = new Supplier();
		sp.setId("test sp");
		
		so.setSupplier(sp);
		
		UnitOfWork uow = SessionManager.getInstance().getSession().acquireUnitOfWork();
		
		uow.deepMergeClone(so);
		
		uow.commit();
	}
}
