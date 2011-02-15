package org.harper.tool.importbook;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.sessions.UnitOfWork;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.SupplyOrder;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.profile.Source;
import org.harper.bookstore.domain.profile.Supplier;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.data.ITable;
import org.harper.frm.data.parser.CSV2TableParser;
import org.harper.frm.toplink.SessionManager;

public class ImportStore {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CSV2TableParser parser = new CSV2TableParser();
		parser.setHeaderExists(true);
		ITable table1 = (ITable) parser.parse(new FileInputStream(
				"book_home.csv"));

		ITable table2 = (ITable) parser.parse(new FileInputStream(
				"book_4_12.csv"));

		UnitOfWork uow = SessionManager.getInstance().getSession()
				.acquireUnitOfWork();

		Supplier sp = new Supplier();
		sp.setId("sp4import");
		sp.setSource(Source.OTHER.name());
		sp.setName("初始库存导入");
		
		TransactionContext.get().push(uow);
		
		SupplyOrder so  = sp.createOrder(null);
		
		StoreSite site = (StoreSite)uow.readObject(StoreSite.class,new ExpressionBuilder().get("name").equal("地下室仓库"));
		
		so.setSite(site);
		so.setCreateDate(new Date());
		List<OrderItem> items = new ArrayList<OrderItem>();
		for (int i = 0; i < table2.getRowCount(); i++) {
			String isbn = (String) table2.getValueAt(i, 1);
			String count = (String) table2.getValueAt(i, 2);
			String price = (String)table2.getValueAt(i, 3);
			
			if(StringUtils.isEmpty(isbn)||StringUtils.isEmpty(price)||StringUtils.isEmpty(count))
			{
				System.out.println(i);
				continue;
			}
			
			Book book = (Book)uow.readObject(Book.class,new ExpressionBuilder().get("isbn").equal(isbn));
			if(null == book)
				throw new IllegalArgumentException(isbn);
			
			OrderItem item = new OrderItem();
			item.setBook(book);
			item.setCount(Integer.parseInt(count));
			item.setUnitPrice(new BigDecimal(price));
			items.add(item);
		}
		so.setItems(items);
		so.setStatus(SupplyOrder.Status.DRAFT.ordinal());
		uow.registerNewObject(so);
		
//		sp = new Supplier();
//		sp.setId("sp4import");
//		sp.setSource(Source.OTHER.name());
//		sp.setName("家里库存导入");
		StoreSite homesite = (StoreSite)uow.readObject(StoreSite.class,new ExpressionBuilder().get("name").equal("家里"));
	
		so = sp.createOrder(null);
		so.setSite(homesite);
		so.setCreateDate(new Date());
		items = new ArrayList<OrderItem>();
		for (int i = 0; i < table1.getRowCount(); i++) {
			String isbn = (String) table1.getValueAt(i, 3);
			String count = (String) table1.getValueAt(i, 1);
			String price = (String)table1.getValueAt(i, 2);
			
			if(StringUtils.isEmpty(isbn)||StringUtils.isEmpty(price))
				continue;
			
			
			Book book = (Book)uow.readObject(Book.class,new ExpressionBuilder().get("isbn").equal(isbn));
			if(null == book)
				throw new IllegalArgumentException(isbn);
			
			OrderItem item = new OrderItem();
			item.setBook(book);
			item.setCount(Integer.parseInt(count));
			item.setUnitPrice(new BigDecimal(price));
			items.add(item);
		}
		so.setItems(items);
		so.setStatus(SupplyOrder.Status.DRAFT.ordinal());
		uow.registerNewObject(so);
		
		uow.commit();

	}

}
