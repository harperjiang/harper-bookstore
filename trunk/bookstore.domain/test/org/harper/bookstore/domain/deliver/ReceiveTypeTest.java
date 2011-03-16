package org.harper.bookstore.domain.deliver;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.harper.bookstore.domain.deliver.ReceiveOrder.ReceiveType;
import org.junit.Test;

public class ReceiveTypeTest {

	@Test
	public void testDesc() {
		Locale.setDefault(Locale.CHINA);
		assertEquals("退货",ReceiveType.RETURN.desc());
	}

}
