package org.harper.bookstore.ui.order;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

public class ViewOrderBeanTest {

	@Test
	public void testGetStopDate() {
		ViewOrderBean bean = new ViewOrderBean();

		Date endOfDay = new Date();
		endOfDay = DateUtils.addSeconds(DateUtils.addDays(
				DateUtils.truncate(endOfDay, Calendar.DATE), 1), -1);

		assertEquals(endOfDay, bean.getStopDate());
	}

}
