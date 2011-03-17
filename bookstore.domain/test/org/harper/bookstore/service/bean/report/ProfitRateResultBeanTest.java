package org.harper.bookstore.service.bean.report;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.harper.bookstore.service.bean.report.ProfitRateResultBean.ProfitRateItemBean;
import org.junit.Test;

public class ProfitRateResultBeanTest {

	@Test
	public void testAddItem() {
		ProfitRateResultBean prrb = new ProfitRateResultBean();
		prrb.addItem(new ProfitRateItemBean(10, BigDecimal.ZERO, BigDecimal.ONE));
		assertEquals("1.0000", prrb.getItems().get(0).getPercentage().toPlainString());
		prrb.addItem(new ProfitRateItemBean(5, BigDecimal.ZERO, BigDecimal.ONE));
		assertEquals("0.6667", prrb.getItems().get(0).getPercentage().toPlainString());
		assertEquals("0.3333", prrb.getItems().get(1).getPercentage().toPlainString());
		prrb.addItem(new ProfitRateItemBean(5, BigDecimal.ZERO, BigDecimal.ONE));
		assertEquals("0.5000", prrb.getItems().get(0).getPercentage().toPlainString());
		assertEquals("0.2500", prrb.getItems().get(1).getPercentage().toPlainString());
		assertEquals("0.2500", prrb.getItems().get(2).getPercentage().toPlainString());
	}

}
