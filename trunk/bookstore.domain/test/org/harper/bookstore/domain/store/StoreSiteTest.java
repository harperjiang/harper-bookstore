package org.harper.bookstore.domain.store;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StoreSiteTest {

	@Test
	public void testAddEntry() {
		StoreSite site = new StoreSite();
		
		StoreEntry entry1 = new StoreEntry();
		site.addEntry(entry1);
		assertEquals(site,entry1.getSite());
		assertEquals(1,site.getEntries().size());
		assertTrue(site.getEntries().contains(entry1));
		
		List<StoreEntry> entries = new ArrayList<StoreEntry>();
		StoreEntry entry2 = new StoreEntry();
		entries.add(entry2);
		StoreEntry entry3 = new StoreEntry();
		entries.add(entry3);
		
		site.setEntries(entries);
		

		assertEquals(site,entry2.getSite());
		assertEquals(site,entry3.getSite());
		assertEquals(2,site.getEntries().size());
		assertTrue(site.getEntries().contains(entry2));
		assertTrue(site.getEntries().contains(entry3));
	}

}
