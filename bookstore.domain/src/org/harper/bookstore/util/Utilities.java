package org.harper.bookstore.util;

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
		Date stopDate = new Date();
		stopDate = DateUtils.addSeconds(DateUtils.addDays(
				DateUtils.truncate(stopDate, Calendar.DATE), 1), -1);
		return stopDate;
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
}
