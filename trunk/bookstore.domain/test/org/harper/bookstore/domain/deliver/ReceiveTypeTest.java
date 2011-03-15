package org.harper.bookstore.domain.deliver;

import static org.junit.Assert.*;

import org.harper.bookstore.domain.deliver.ReceiveOrder.ReceiveType;
import org.junit.Test;

public class ReceiveTypeTest {

	@Test
	public void testDesc() {
		assertEquals("",ReceiveType.RECEIVE.desc());
	}

}
