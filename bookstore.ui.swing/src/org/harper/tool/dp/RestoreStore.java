package org.harper.tool.dp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.queryframework.ReadAllQuery;
import oracle.toplink.sessions.Session;
import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.domain.library.BorrowRecord;
import org.harper.bookstore.domain.library.Record;
import org.harper.bookstore.domain.library.ReturnRecord;
import org.harper.bookstore.domain.order.Order;
import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.store.Transfer;
import org.harper.bookstore.service.LibraryService;
import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.toplink.SessionManager;

public class RestoreStore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session ssn = SessionManager.getInstance().getSession();

		Map<StoreSite, Map<Book, Integer>> bookmap = new HashMap<StoreSite, Map<Book, Integer>>();

		UnitOfWork uow = ssn.acquireUnitOfWork();
		TransactionContext.get().push(uow);
		List<Order> sos = (List<Order>) uow.readAllObjects(Order.class,
				new ExpressionBuilder().getField("order_type").equal("S"));

		for (Order order : sos) {
			if (order instanceof SupplyOrder
					&& order.getStatus() == Order.Status.CONFIRM.ordinal()) {
				order.setStatus(Order.Status.DRAFT.ordinal());
				((SupplyOrder) order).confirm();
			}
		}
		uow.commitAndResume();
		List<Order> pos = (List<Order>) uow.readAllObjects(Order.class,
				new ExpressionBuilder().getField("order_type").equal("B"));

		for (Order order : pos) {
			try {
				if (order instanceof PurchaseOrder) {
					if (order.getStatus() == Order.Status.CONFIRM.ordinal()) {
						order.setStatus(Order.Status.NEW.ordinal());
						((PurchaseOrder) order).place();
						((PurchaseOrder) order).confirm();
					}
					if (order.getStatus() == Order.Status.DRAFT.ordinal()) {
						order.setStatus(Order.Status.NEW.ordinal());
						((PurchaseOrder) order).place();
					}
				}

			} catch (RuntimeException e) {
				System.out.println(order.getNumber());
				e.printStackTrace();
				throw e;
			}
		}

		List<Transfer> transfers = (List<Transfer>) uow
				.readAllObjects(Transfer.class);
		for (Transfer trans : transfers) {
			if (trans.getStatus() == Transfer.Status.CONFIRM) {
				trans.setStatus(Transfer.Status.DRAFT.ordinal());
				trans.confirm();
			}
		}
		
		ReadAllQuery raq = new ReadAllQuery(Record.class);
		raq.addOrdering(new ExpressionBuilder().get("accountDate"));
		
		List<Record> records = (List<Record>)uow.executeQuery(raq); 
		
		LibraryService ls = new LibraryService();
		for(Record rec:records) {
			if(rec instanceof BorrowRecord) {
				((BorrowRecord) rec).proceed();
			} else if(rec instanceof ReturnRecord) {
				((ReturnRecord) rec).proceed();
				((ReturnRecord) rec).sweep();
			}
				
		}

		uow.commit();

	}

	static void add(Map<Book, Integer> map, Book book, int count) {
		if (!map.containsKey(book)) {
			map.put(book, 0);
		}
		map.put(book, map.get(book) + count);
	}
}
