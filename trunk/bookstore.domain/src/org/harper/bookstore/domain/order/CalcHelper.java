package org.harper.bookstore.domain.order;

import java.math.BigDecimal;
import java.util.List;

import org.harper.bookstore.domain.profile.BookUnit;

public class CalcHelper {

	static final int SCALE = 2;
	/**
	 * We accept the discrepancy during the final calculation
	 * @param total
	 * @param percentage
	 * @return
	 */
	public static BigDecimal[] split(BigDecimal total, List<BookUnit> percentage) {
		BigDecimal[] result = new BigDecimal[percentage.size()];

		BigDecimal sum = BigDecimal.ZERO;
		for (BookUnit single : percentage) {
			sum = sum.add(single.getUnitPrice());
		}
		BigDecimal subtotal = BigDecimal.ZERO.setScale(2);
		for (int i = 0; i < result.length; i++) {
			BookUnit bu = percentage.get(i);
			BigDecimal buCount = new BigDecimal(bu.getCount()).setScale(SCALE);
			result[i] = bu.getUnitPrice().setScale(SCALE).divide(sum.setScale(SCALE), BigDecimal.ROUND_HALF_UP)
					.multiply(total).divide(buCount);
			subtotal = subtotal.add(result[i].multiply(buCount));
		}
		result[result.length - 1].add(total.subtract(subtotal));

		return result;
	}
}
