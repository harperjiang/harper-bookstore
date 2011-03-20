package org.harper.bookstore.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.harper.frm.core.tools.bean.Base64Encoding;

public class Utilities {

	public static Date getBeginOfDate(int prev) {
		Date stopDate = new Date();
		stopDate = DateUtils.addDays(
				DateUtils.truncate(stopDate, Calendar.DATE), -prev);
		return stopDate;
	}

	public static Date getEndOfDate() {
		return getEndOfDate(new Date());
	}

	public static Date getEndOfDate(Date date) {
		if (null == date)
			return null;
		date = DateUtils.addSeconds(
				DateUtils.addDays(DateUtils.truncate(date, Calendar.DATE), 1),
				-1);
		return date;
	}

	public static String digest(String input) {
		try {
			byte[] result = MessageDigest.getInstance("MD5").digest(
					input.getBytes());
			return new String(Base64Encoding.encode(result));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String percentage(Number value) {
		BigDecimal bd = value instanceof BigDecimal ? (BigDecimal) value
				: new BigDecimal(value.doubleValue());
		bd = bd.multiply(HUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP);
		String result = bd.toPlainString() + "%";
		return result;
	}

	protected static BigDecimal HUNDRED = new BigDecimal("100");

}
