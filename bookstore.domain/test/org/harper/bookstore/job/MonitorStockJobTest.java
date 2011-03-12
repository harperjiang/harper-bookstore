package org.harper.bookstore.job;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StockAlert;
import org.harper.bookstore.domain.store.StoreEntry;
import org.harper.bookstore.domain.store.StoreSite;
import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.bookstore.repo.mem.MemoryCommonRepo;
import org.harper.bookstore.repo.mem.MemoryStoreRepo;
import org.harper.bookstore.repo.mem.MemoryTodoRepo;
import org.harper.mediator.MediatorEvent;
import org.junit.Test;

public class MonitorStockJobTest {

	@Test
	public void testSiteThreshold() {
		MemoryCommonRepo mcr = new MemoryCommonRepo();
		MemoryStoreRepo msr = new MemoryStoreRepo();
		msr.setCommonRepo(mcr);
		MemoryTodoRepo mtr = new MemoryTodoRepo();
		mtr.setCommonRepo(mcr);

		MonitorStockJob job = new MonitorStockJob();
		job.setCommonRepo(mcr);
		job.setStoreRepo(msr);
		job.setTodoRepo(mtr);

		// Test Data
		StoreSite site = new StoreSite();
		site.setOid(1);
		site.setName("A Site");
		StoreEntry entry = new StoreEntry();
		entry.setOid(1);
		Book book = new Book();
		book.setOid(1);
		book.setIsbn("123");
		book.setName("New Book");
		entry.setBook(book);
		entry.setCount(10);
		site.addEntry(entry);

		StockAlert sasite = new StockAlert();
		sasite.setSite(site);
		sasite.setBook(book);
		sasite.setWarnThreshold(15);
		sasite.setErrorThreshold(0);

		mcr.store(book);
		mcr.store(site);
		mcr.store(entry);
		mcr.store(sasite);

		job.setTriggerEvent(new MediatorEvent(entry));

		job.call(null);

		List items = mcr.getStores().get(TodoItem.class);
		assertEquals(1, items.size());
		TodoItem inserted = (TodoItem) items.get(0);
		assertEquals("Book \"New Book\" Out of Stock", inserted.getSubject());
		assertEquals(
				"Book:New Book\nStore Site:A Site\nLevel:Alert\nThreshold:15\nCurrent:10",
				inserted.getContent());
		assertEquals(Privilege.HIGH, inserted.getPrivilege());

		sasite.setErrorThreshold(11);

		job.call(null);

		items = mcr.getStores().get(TodoItem.class);
		assertEquals(1, items.size());
		inserted = (TodoItem) items.get(0);
		assertEquals("Book \"New Book\" Out of Stock", inserted.getSubject());
		assertEquals(
				"Book:New Book\nStore Site:A Site\nLevel:Severe\nThreshold:11\nCurrent:10",
				inserted.getContent());
		assertEquals(Privilege.URGENT, inserted.getPrivilege());
	}

	@Test
	public void testGlobalThreshold() {
		MemoryCommonRepo mcr = new MemoryCommonRepo();
		MemoryStoreRepo msr = new MemoryStoreRepo();
		msr.setCommonRepo(mcr);
		MemoryTodoRepo mtr = new MemoryTodoRepo();
		mtr.setCommonRepo(mcr);

		MonitorStockJob job = new MonitorStockJob();
		job.setCommonRepo(mcr);
		job.setStoreRepo(msr);
		job.setTodoRepo(mtr);

		// Test Data
		StoreSite site = new StoreSite();
		site.setOid(1);
		site.setName("A Site");
		StoreEntry entry = new StoreEntry();
		entry.setOid(1);
		Book book = new Book();
		book.setOid(1);
		book.setIsbn("123");
		book.setName("New Book");
		entry.setBook(book);
		entry.setCount(10);
		site.addEntry(entry);

		StockAlert sasite = new StockAlert();
		sasite.setSite(null);
		sasite.setBook(book);
		sasite.setWarnThreshold(15);
		sasite.setErrorThreshold(0);

		mcr.store(book);
		mcr.store(site);
		mcr.store(entry);
		mcr.store(sasite);

		job.setTriggerEvent(new MediatorEvent(entry));

		job.call(null);

		List items = mcr.getStores().get(TodoItem.class);
		assertEquals(1, items.size());
		TodoItem inserted = (TodoItem) items.get(0);
		assertEquals("Book \"New Book\" Out of Stock", inserted.getSubject());
		assertEquals(
				"Book:New Book\nStore Site:---\nLevel:Alert\nThreshold:15\nCurrent:10",
				inserted.getContent());
		assertEquals(Privilege.HIGH, inserted.getPrivilege());

		sasite.setErrorThreshold(11);

		job.call(null);

		items = mcr.getStores().get(TodoItem.class);
		assertEquals(1, items.size());
		inserted = (TodoItem) items.get(0);
		assertEquals("Book \"New Book\" Out of Stock", inserted.getSubject());
		assertEquals(
				"Book:New Book\nStore Site:---\nLevel:Severe\nThreshold:11\nCurrent:10",
				inserted.getContent());
		assertEquals(Privilege.URGENT, inserted.getPrivilege());
	}
}
