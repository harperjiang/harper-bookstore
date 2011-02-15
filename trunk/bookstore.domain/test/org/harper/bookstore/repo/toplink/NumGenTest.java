package org.harper.bookstore.repo.toplink;

import static org.junit.Assert.assertEquals;

import org.harper.bookstore.repo.toplink.TopLinkCommonRepo.NumGen;
import org.junit.Test;

public class NumGenTest {

	@Test
	public void testNextNumber() {
		NumGen gen = new NumGen();
		gen.setCurrent(1);
		gen.setPrefix("PO");
		gen.setLength(10);
		
		assertEquals("PO00000001",gen.nextNumber());
		assertEquals("PO00000002",gen.nextNumber());
		assertEquals("PO00000003",gen.nextNumber());
		assertEquals("PO00000004",gen.nextNumber());
		assertEquals("PO00000005",gen.nextNumber());
	}

}
