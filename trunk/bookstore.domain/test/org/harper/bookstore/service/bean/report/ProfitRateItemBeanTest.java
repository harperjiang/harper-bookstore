package org.harper.bookstore.service.bean.report;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.harper.bookstore.service.bean.report.ProfitRateResultBean.ProfitRateItemBean;
import org.junit.Test;

public class ProfitRateItemBeanTest {

	@Test
	public void testGetRangeDesc() {
		ProfitRateItemBean bean = new ProfitRateItemBean(10, new BigDecimal(
				"0.1"), new BigDecimal("0.2"));
		assertEquals("10.00%-20.00%", bean.getRangeDesc());
	}

}
